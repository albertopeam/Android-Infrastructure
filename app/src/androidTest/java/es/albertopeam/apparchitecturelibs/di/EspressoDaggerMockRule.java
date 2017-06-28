package es.albertopeam.apparchitecturelibs.di;

import android.support.test.InstrumentationRegistry;

import es.albertopeam.apparchitecturelibs.App;
import it.cosenonjaviste.daggermock.DaggerMockRule;

/**
 * Created by Alberto Penas Amor on 21/06/2017.
 * This rule overrides AppComponent and can override any object provided by it and any
 * of its subcomponents provided objects. To override a provided object its mandatory to define
 * with a mock object with the @Mock annotation inside the test. The {@link DaggerMockRule} class
 * will check @Mock objects inside the and will replace the provided dependencies with test mocks
 *
 * Tricks:
 * <p>
 *     Avoid use MockitoAnnotation.init(this); because {@link DaggerMockRule} will do it for us,
 * otherwiser the mock objects will be different in test and module.
 * </p>
 * <p>
 *     Not leave Espresso to start the activity through the
 *     {@link android.support.test.rule.ActivityTestRule} beacuse we need to run before the
 *     {@link EspressoDaggerMockRule}, otherwise the mock objects will not be created before the
 *     activity was launched.
 * </p>
 */

public class EspressoDaggerMockRule
        extends DaggerMockRule<AppComponent>{

    public EspressoDaggerMockRule() {
        super(AppComponent.class, new AppModule(getApp()));
        set(new DaggerMockRule.ComponentSetter<AppComponent>() {
            @Override public void setComponent(AppComponent component) {
                getApp().container().setAppComponent(component);
            }
        });
    }

    private static App getApp() {
        return (App) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }
}
