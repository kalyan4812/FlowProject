package com.example.flowproject.di.modules

/*

 * try to scope dependencies to minimum lifecycle.

 * use singleton only if we need instances throughout the app,

 * singleton,viewmodel scoped,activity scoped,fragment scoped,service scoped....

 * using ktx dependencies you can get instances of viewmodels in frag,activity.

 * if you want to share a dependency across two/more fragments create a group/nest of those
  fragments.(viewmodel bound to set of fragments )

  and use the code in fragments.

  val viewmodel:XXViewmodel by navGraphViewModels(R.navigation.nested_nav)

*

  val fragmentScopedViewmodels  by viewModels<SampleViewModel>() // This extension is available in both activity and fragment
  val activityScopedViewmodels by activityViewModels<SampleViewModel>()




 */