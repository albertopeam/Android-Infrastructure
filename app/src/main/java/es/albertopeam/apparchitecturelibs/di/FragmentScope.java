package es.albertopeam.apparchitecturelibs.di;

/**
 * Created by Adrian on 18/4/16.
 */

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the fragment to be memorized in the
 * correct component.
 */
@Scope
@Retention(RUNTIME)
public @interface FragmentScope {}