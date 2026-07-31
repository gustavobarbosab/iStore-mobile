# iStore

A checkout app built as a learning exercise: a simple e-commerce flow (browse → detail → checkout →
confirmation → order history) used to explore a realistic mobile + backend architecture — a KMP/
Compose Multiplatform client talking to a BFF behind an API Gateway, with async payment processing
backed by a message queue and a worker (no polling on the client).

This repo currently contains the **mobile client**. The backend (Gateway, BFF, Payment API, queue,
worker) is documented but not yet implemented — all data is mocked in-process (see
[Architecture](#architecture) below).

## Screens

- **Home** — product list.
- **Detail** — single product, "Comprar" starts checkout.
- **Checkout** — order summary, confirms payment.
- **Confirmation** — shows "payment processing", never polls for a result.
- **Meus Pedidos (History)** — order list; this is where the payment result (approved/declined)
  actually shows up, resolved on-demand when the screen is opened.
- **Profile** — static user info.

Home / Meus Pedidos / Profile are top-level tabs (bottom navigation bar); Detail, Checkout, and
Confirmation are pushed on top without the bottom bar.

## Architecture

Clean Architecture (`domain` / `data` / `ui`) with MVI on every screen, Koin for DI, Compose
Navigation for routing. Full conventions, do's/don'ts, and file-naming rules live in
[`.sage/harness.md`](./.sage/harness.md) — read that before adding a screen or a layer. Short
version:

- **`domain`** — pure Kotlin models, repository interfaces, and use cases (`GetProductsUseCase`,
  `CheckoutUseCase`, etc.).
- **`data`** — `*RepositoryImpl` orchestrating a `*LocalDataSource` (in-memory cache) and a
  `*RemoteDataSource` (mocked BFF calls, simulated with `delay`). `PaymentRepositoryImpl` also
  simulates the payment worker: after `checkout()` returns, a background coroutine "resolves" the
  order status a few seconds later, so opening Meus Pedidos later shows the real outcome — the
  same "no polling" behavior the real architecture is designed around.
- **`ui`** — one package per screen (`ui/screen/<feature>/`), each with an `*Arch.kt` (sealed
  `UiState`/`Event`/`SideEffect`), a `*ViewModel.kt`, a `*ScreenContent.kt` that only routes state,
  a `mapper/*UiModelMapper.kt` (domain → UI model, never inlined in the ViewModel), and a
  `component/` subpackage for the actual reusable composables (cards, lists, badges).

Navigation between top-level tabs always goes through a single `navigateToTopLevel()` helper
(clears the back stack before pushing) instead of ad-hoc `popUpTo` calls per screen.

## Tech stack

- Kotlin Multiplatform + Compose Multiplatform (Android target active; iOS scaffolded, not yet
  wired to the same UI code — screens currently live in `androidApp`, not `shared`).
- Koin (DI), Compose Navigation (typed routes via `kotlinx.serialization`).
- Material3, with a custom red-based `ColorScheme` (`ui/theme/`) instead of the default baseline
  purple.

## Project structure

* [/androidApp](./androidApp/src/main/kotlin) — the actual app: screens, navigation, DI, domain
  and data layers (see [Architecture](#architecture)).
* [/shared](./shared/src) — Compose Multiplatform code meant to be shared across targets. Reserved
  for when the domain/data/ui layers above get moved here to light up iOS; currently holds only the
  KMP project template's default content.
* [/iosApp](./iosApp/iosApp) — iOS entry point (SwiftUI shell required even when sharing UI via
  Compose Multiplatform). App icon is generated from the same logo as Android's.

## Running the apps

Use the run configurations in your IDE's toolbar, or:

- Android app: `./gradlew :androidApp:assembleDebug`
- iOS app: open [/iosApp](./iosApp) in Xcode and run it from there (note: iOS won't show the actual
  screens yet — see the module note above).

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
