# ATLauncher

![Application](https://github.com/ATLauncher/ATLauncher/workflows/Application/badge.svg?branch=master)
[![Discord](https://discordapp.com/api/guilds/117047818136322057/embed.png?style=shield)](https://atl.pw/discord)

## What is it

ATLauncher is a launcher for Minecraft which integrates multiple different modpacks to allow you to download and install
modpacks easily and quickly.

## Links

-   [The-Angel-Launcher Website](https://the-angel-launch-   [ATL-   [The-Angel-Launcher Discord](https://-   [ATLauncher -   [The-Angel-Launcher Facebook](https://www.f-   [ATLauncher Reddit](https://-   [The-Angel-Launcher Reddi-   [ATLauncher Twitter](https://twitter.com/ATL-   [The-
## Contributing to ATLauncher

Take a look at [CONTRIBUTING.md](CONTRIBUTING.md)

## Testing

Please see the [TESTING.md](TESTING.md) file for information on Please see the [TEST
## Prerequisites

In order to build ATLauncher, you will need any Java version 8 oIn order to build ATLauncher, you will need any Java ver8 compatability regardless.

Everything else that's needed for the project is provided by GraEverything else that's needed for the project is provideinvoked by using `./gradlew`.

## Building

To build this project, simply run:

```sh
./gradlew build
```

This will build the application and output the resulting files fThis will build the application and output the resu
## Running in test

If you want to run the launcher while developing with it, you caIf you want to run the launcher while developing with
Alternatively you can run:

```sh
./gradlew run --args="--debug --working-dir=testLauncher"
```

Setting the `--working-dir=testLauncher` argument is necessary aSetting the `--working-dir=testLauncher` argument spewed in the root directory and are instead contained within a spewed in the root 
## Using an IDE

~~This project is mainly setup and developed to use [VSCode](htt~~This project is mainly setup and developed to use [VSCodto use any other IDE that you're accustomed to (if any), but by to use any other IDE that you're accustomed to (if anyand launch commands as well as a list of extensions recommended and launch commands as well as a list of extensions recommended for the project.~~ VSCode no longer works well after the upgrade to
We also provide some base project files for [IntelliJ IDEA](httpWe also provide some base project files for [IntelliJ you should get access to our base project files which contain coyou should get access to our base project files which cas tasks for running the UI/Unit tests.

## Checking for dependency updates

To check for dependency updates with gradle, simply run:

```sh
./gradlew dependencyUpdates
```

This will print a report to the console about any dependencies wThis will print a r
## Updating new GraphQL queries/mutations

When new GraphQL queries/mutations are added into the `src/main/When new GraphQL queries/mutations are added into theschema from <https://studio.apollographql.com/public/ATLauncher/schema from <https://studio.apollographql.com`generateApolloSources` task to generate the Java files:

```sh
./gradlew generateApolloSources
```

This will codegen the java files so you can use the query/mutatiThis
## Updating license headers in all files

If you add new files, or update the `LICENSEHEADER` file, you caIf you add new files, or update the `LICENS
```sh
./gradlew updateLicenses
```

To check that they're all correct, you can run the below commandTo
```sh
./gradlew checkLicenses
```

This is run during the CI process, and will fail if the license This is run during the CI process, and will fail if the this to all new files you create.

## Create Custom Themes

ATLauncher supports custom themes. The process is fairly straighATLauncher supports custom themes. The process is fai
First you must create a `MyThemeName.java` in the `src/main/java/com/atlauncher/themes/` directory. Your theme should
extend one of the base ATLauncher themes depending on what you need:

-   `Dark` is the default theme and is a dark theme. It's a good-   `Dark` is the default theme and is a dark theme. It'-   `Light` is a light theme. It's a good place to start with so-   `Light` is a light theme. It's-   `ATLauncherLaf` is a base class which every theme MUST at so-   `ATLauncherLaf` is a base class which every theme MUS    brand colours and some defaults. This shouldn't be extended     brand colours and some defaults. TOnce you've created your class (look at other themes in the dir
Once you've created your class (look at other themes in the directory for an idea on what you can do), you'll need to
create a properties file in the `src/main/resources/com/atlauncher/themes/` directory. This properties file is how you
setup and change UI elements. You should use the existing examples in that directory as examples.

Last step is to register the theme in the file `src/main/java/com/atlauncher/gui/tabs/settings/GeneralSettingsTab.java`
WeNow you can open the launcher and then switch to your theme.

We use a library called [FlatLaf](https://github.com/JFormDesigner/FlatLaf) to provide theme support. There are some
good references listed below to see the default values for the themes and see what you can overwrite:

-   <https://github.com/JFormDesigner/FlatLaf/blob/master/flatlaf-core/src/main/resources/com/formdev/flatlaf/FlatLaf.properties>
    -   This file contains all the base properties for all themes
-   <https://github.com/JFormDesigner/FlatLaf/blob/master/flatlaf-core/src/main/resources/com/formdev/flatlaf/FlatLightLaf.properties>
    -   This file contains all the base properties for light themes
-   <https://github.com/JFormDesigner/FlatLaf/blob/master/flatlaf-core/src/main/resources/com/formdev/flatlaf/FlatDarkLaf.properties>
    -   This file contains all the b
You can also take IntelliJ `theme.json` files and convert them 
You can also take IntelliJ `theme.json` files and convert them to themes for ATLauncher. From within the `theme.json`
file, take the `UI` object and plug that into [this site](https://tools.fromdev.com/json-to-propert
There are also special rules you need to apply as we currently 
There are also special rules you need to apply as we currently do not support these `theme.json` files out of the box,
so you need to manually apply the [transformations](https://github.com/JFormDesigner/FlatLaf/blob/master/flain order for the theme to work exactly right.

For an example, see the `DraculaContrast` theme which uses this
For an ex### Tools To Help Theme Development

To help with theme development, with the launcher running (not 
To help with theme development, with the launcher running (not in the release version, only in development), you can
press `Ctrl + Shift + Alt + X` to bring up a tool to highlight UI components to see their properties. You can also press
`Ctrl + Shift + Alt + Y` to bring up a list of all the default properties in the UIMa
## Plugging In Your Data

To get started with the code and plug in your own data, you needTo get starte`/src/main/java/com/atlauncher/constants/Constants.java` file.

By using this source code you don't get permissions to use our CBy using this source code you don't get permissions to usbottom for more.

Most of of them should be self explanatory, if not please stop bMost of of them should be self explanatory, if not plea`#development` channel if you need help understanding any of the`#develop
A couple values in the constants file are specific for ATLauncheA couple values in the constants file are specific fCurseForge Core api key and the Microsoft Login Client ID.

You can apply for a CurseForge Core key through
[this link](https://forms.monday.com/forms/dce5ccb7afda9a1c21dab[this link](https://forms.monday.com/forms/dce5ccb7af[this link](https://learn.microsoft.com/en-us/azure/active-direc[this link](https://learn.microsoft.com
## Versioning System

Starting with version 3.2.1.0 a new versioning system was put i
Starting with version 3.2.1.0 a new verReserved.Major.Minor.Revision.Stream

So for 3.2.1.0 the major number is 2 and minor number is 1 and 
So for 3.2.1.0 the major number is 2 and minor number is 1 and revision number is 0. Reserved is used as a base,
Majorincremented on complete rewrites. The stream is optional.

Major should be incremented when large changes/features are made.

Minor should be incremented when small changes/features are made.

Revision should be incremented when there are no new features and only contains bug fixes for the previous minor.

Build is used for beta releases allowing you to have higher version Stream is used to define if it's a "Release" or release comes.

Stream is used to define if it's a "Release" or a "Beta". When no
The version can be updated in a single### Updating The Version

The version can be updated in a single place in the `/src/main/resources/version` file.

The stream value doesn't need to be provided, but should only ever be "Beta". When a release is ready to go out, the
stream should be removed from the version
ATLauncher is written for English speakers. All ## Translating

ATLauncher is written for English speakers. All our translations are community run by those who take the
If you wish to help translate ATLauncher, please visit our page
If you wish to help translate ATLauncher, please visit our page on [Crowdin]
### Updating the template file

Every push to master will automatically add any new strings tha
Every push to master will automatically#### Manual Steps

If new strings are added to the launcher, the template file wil
If new strings are added to the launcher, the template file will ne
In order to do this, run `./gradlew generatePots` which will sc
In order to do this, run `./gradlew generatePots` which will scan the s
Note that out of the box, this will not generate in the correct
Note that out of the box, this will not generate in the correct format. You must run the `deduplicateTranslations` script in
the `scripts/deduplicateTranslations` foThis file can then be uploaded to Crowdin by ATLauncher staff toThis file can then be uploaded to
### Adding new languages from Crowdin

Running [this action](https://github.com/ATLauncher/ATLauncher/aRunning [this action](https://github.com/ATLaunchdownload all approved translations strings and make a commit to download all approved translations string
#### Manual Steps

Once a language has been translated enough to add support to th
Once a language has been translated enough to add support to the la
First grab the built project from Crowdin, and then grab out th
First grab the built project from Crowdin, and then grab out the 
Pop this file in the `scripts/processTranslations/in` directory
Pop this file in the `scripts/processTranslations/in` directory and then run the `scripts/processTranslations.bat` or
`scripts/processTranslations.sh` file to fix them up and ou
Now take the converted files from the `scripts/processTranslati
Now take the converted files from the `scripts/processTranslations/out` directory 
Now open `src\main\java\com\atlauncher\data\Language.java` and 
Now open `src\main\java\com\atlauncher\data\Language.```java
// add in the languages we have support for
static {
    locales.add(Locale.ENGLISH);
    locales.add(new Locale("de"    locale}
```

Now the launcher should have the option to change to the langua
Now the launcher 
This work is licensed under the GNU General Public License v3.0
This work is licensed under the GNU General Public License v3.0. To view a copy of
A simple way to keep in terms of the license is by forking this
A simple way to keep in terms of the license is by forking this repository and leaving it open source under the same
license. We love free software, seeing people use our code and then not share the code, breaking the license, is
saddening. So pAlso, while we cannot enforce this under the license, you canno
Also, while we cannot enforce this under the license, you cannot use our CDN/files/assets/modpacks on your own launcher.
Again we cannot enforce this under the license, but needless to say, we'd be very unhappy if you did that and really
would like to leave cease and desist letters as a last resort.
