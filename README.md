[![Android Gems](http://www.android-gems.com/badge/KeithYokoma/CompoundContainers.svg?branch=master)](http://www.android-gems.com/lib/KeithYokoma/CompoundContainers)

CompoundContainers
==================

Checkable implementation for various view groups and RadioGroup optimization for them.

## Supported view groups

There're basic `Checkable` view groups.

- CompoundFrameLayout
- CompoundLinearLayout
- CompoundRelativeLayout
- CompoundGridLayout
- CompoundViewGroup

We also support `RadioButton` behaviour on the view groups.

- RadioFrameLayout
- RadioLinearLayout
- RadioRelativeLayout
- RadioGridLayout

If you want to listen to the checked state change, you can use `OnCheckedChangeListener` and register it to one of these views above.

## Download

Via Gradle

```groovy

compile 'com.github.keithyokoma:CompoundContainers:1.0.0@aar'
```

## License

Apache v2

```
Copyright (C) 2015 KeithYokoma, Inc. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
```
