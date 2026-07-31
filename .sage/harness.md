# Harness — eFit

## Project

- **App:** iStore
- **Package:** `io.github.gustavobarbosab.istore`
- **Description:** A powerful app to track workout progress.

## SDK & Versions

| Key        | Value  |
|------------|--------|
| minSdk     | 24     |
| targetSdk  | 34     |
| compileSdk | 35     |
| Kotlin     | 2.3.10 |
| AGP        | 8.8.1  |

## Architecture

**Pattern:** MVI (Model-View-Intent) across all screens.

**Layers:**

- `presentation` — screens, ViewModels, navigation, design system, theme.
- `data` — repositories, datasources (local/remote), DTOs, storage, JSON parsing.

**MVI contract — every screen defines an `*Arch.kt` file with:**

- `sealed class *UiState` — all possible UI states (e.g. `Loading`, `Ready`, `Error`).
- `sealed class *SideEffect` — one-shot events emitted to the UI (e.g. navigation, toasts).
- `sealed class *Event` — user intent dispatched to the ViewModel.
- `class *Mvi : MviDelegateImpl<UiState, SideEffect>` — the screen's delegate instance.

**MVI base classes (`io.github.gustavobarbosab.istore.common`):**

- `MviDelegate<State, Effect>` — interface exposing `state: StateFlow`, `sideEffect: SharedFlow`,
  `onState()`, `onSideEffect()`.
- `MviDelegateImpl<State, Effect>` — abstract class implementing `MviDelegate`; extended by each
  screen's `*Mvi` class.
- `MviEventHandler<Event>` — interface enforcing `onEvent()` on the ViewModel.

**ViewModel pattern:**

```kotlin
class MyViewModel(
    private val myMvi: MyMvi
) : ViewModel(),
    MviDelegate<MyUiState, MySideEffect> by myMvi,
    MviEventHandler<MyEvent> {

    override fun onEvent(event: MyEvent) {
        // Implementation here
    }
}
```

**Navigation:** Compose Navigation with typed destinations defined in `Destination.kt`.

**State management:** `StateFlow` for UI state, `SharedFlow` for one-shot side effects, managed via
the `MviDelegate` / `MviDelegateImpl` pattern.

## UI

- **Framework:** Compose Multiplatform (Android + iOS).
- **Design system:** Custom DS wrapping Material components. Reusable components live in
  `presentation/designsystem/component/`; custom modifiers in `presentation/designsystem/modifier/`.
- **Theming:** `AppTheme` wraps `MaterialTheme`, injecting custom color tokens and typography via
  `CompositionLocalProvider` (`LocalAppColorContext`, `LocalTypography`). Dark/light theme supported
  via `ThemeContext`.

## Dependency Injection

- **Framework:** Koin Multiplatform.
- **Scoping:**
    - `single` — core/app-wide dependencies only (e.g. storage, JSON parser, dispatcher provider).
    - `factory` — screen-scoped dependencies and `*Mvi` delegates.
    - `viewModelOf` — ViewModels.
- **Module structure:** One `object *Module { val module = module { ... } }` per feature, in a `di/`
  subpackage within the feature package.
- **Entry point:** `EfitKoinApplication` — a `@Composable` that bootstraps
  `KoinMultiplatformApplication` with all modules assembled.

## Networking & Data

- **HTTP client:** Ktor (planned — not yet active).
- **Serialization:** `kotlinx.serialization`.
- **Persistence:** AndroidX DataStore via `EFitStorage` wrapper.
- **Pattern:** Repository pattern with `*LocalDataSource` and `*RemoteDataSource` per domain.
- **Caching:** (TBD)

## Async & Concurrency

- **Async:** Kotlin Coroutines + Flow.
- **Flows:** `StateFlow` for state, `SharedFlow` for side effects.
- **Scopes:** `viewModelScope` in ViewModels.
- **Dispatchers:** Abstracted via `CoroutineDispatcherProvider` — `Main` for UI, `IO` for data
  operations, `Default` for CPU-bound work.

## Testing

- **Unit tests:** JUnit4 + MockK.
- **Instrumented tests:** Yes.
- **UI tests:** (TBD)
- **Coverage goals:** (TBD)

## Do NOT

- Put business logic inside Composables or Screen functions.
- Put DTO-to-UI-model mapping logic inside ViewModels — use a dedicated `*DtoToUiModelMapper`.
- Write inline date/time format logic or private formatting extensions — use `DateFormatter`.
- Skip the `*Arch.kt` contract — every screen must define sealed `UiState`, `Event`, and
  `SideEffect`.
- Let DTOs appear in `UiState`, `SideEffect`, or Composables — always map to a dedicated UI model
  first.
- Call repositories directly from the UI layer — always go through the ViewModel.
- Launch coroutines in `GlobalScope` — use `viewModelScope`.
- Block the main thread with `runBlocking`.
- Collect flows in Composables without `LaunchedEffect` or `collectAsStateWithLifecycle`.
- Use `mutableStateOf` in ViewModels — use `StateFlow`.
- Use `single` for screen-scoped dependencies — use `factory`.
- Instantiate ViewModels manually — use `koinViewModel()`.
- Inject Android-specific types in `commonMain` modules.
- Put stateful logic inside Composables — hoist state to the ViewModel.
- Use `remember` for data that should survive recomposition.
- Use platform-specific APIs in `commonMain` without `expect/actual`.

## Best Practices & Conventions

- **Screen package structure:** `presentation/screen/<domain>/<action>/` — one package per screen.
- **File naming per screen:** `<Feature><Action>Arch.kt`, `<Feature><Action>Screen.kt`,
  `<Feature><Action>ViewModel.kt`, `di/<Feature><Action>Module.kt`.
- **Data entities:** Suffixed `*Dto`, live in `data/entity/`.
- **Repositories:** `*Repository.kt` in `data/<domain>/repository/`.
- **Datasources:** `*LocalDataSource.kt` / `*RemoteDataSource.kt` in
  `data/<domain>/datasource/local|remote/`.
- **DI modules:** Always `object` with a `val module` property — one per feature in a `di/`
  subpackage.
- **Mappers:** All DTO-to-UI-model mapping must live in a dedicated `*DtoToUiModelMapper` class
  inside a `mapper/` subpackage of the screen package (e.g.
  `presentation/screen/home/mapper/WorkoutProgressDtoToStreakMapper`). ViewModels must never
  contain mapping logic — inject the mapper as a constructor parameter and delegate to it.
  Register mappers as `factory` in the feature's DI module.
- **Date & time formatting:** All date/time display formatting must go through the single
  `DateFormatter` class (`data/common/date/DateFormatter.kt`), registered as `single` in
  `DateModule` (included in `DataModule`). Inject it wherever formatting is needed — never
  write inline format logic or private formatting extensions outside of `DateFormatter`.
- **Design system:** All reusable UI components go in `presentation/designsystem/`; never build
  one-off styled components inline in screen files.
- **Theme tokens:** Always use `AppTheme` tokens (spacing, sizing, color, typography) — never
  hardcode values.
- **Code style:** No linter enforced.
