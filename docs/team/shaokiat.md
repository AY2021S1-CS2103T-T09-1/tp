---
layout: page
title: Lim Shao Kiat's Project Portfolio Page
---

## Project: GradPad

GradPad is a one-stop solution to **module management** for Computer Science Undergraduates.
GradPad **consolidates** the modules you have taken and **displays** the remaining required modules to
ease your module planning process. A **module searching platform** is also included in GradPad, providing easy
**navigation** of modules.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=shaokiat&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=zoom&zA=shaokiat&zR=AY2021S1-CS2103T-T09-1%2Ftp%5Bmaster%5D&zACS=239.02341137123747&zS=2020-08-14&zFS=t09&zU=2020-11-05&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false)
* **New Feature**: Added a `search` feature to search for modules from NUSMods and display it to the user. [\#100](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/100)
  * What it does: Allows users to search for module details using module code and displaying all module information
    onto the Result Display. Module information are fetch directly from NUSMods if internet is available, else it will 
    be fetched from local saved JSON file.
  
  * Justification: This feature is required for users to look up relevant module details that they want to find out
   about when planning for which modules to take. This command displays the module code, module title, modular credits,
   semesters, descriptions, prerequisites and preclusion directly from NUSMods. 
  
  * Highlights: To implement this feature, I created a logic class `ModuleInfoSearcher` to utilize the nusmods package
   previously created by my team mates. This logic class make method calls `NusmodsDataManager` to retrieve module 
   information directly from NUSMods using the NUSMods API. The search command then utilize `ModuleInfoSearcher` class
   to search for module information and display the information accordingly in the Result Display.

* **Team tasks**:
  * Updated the User Guide according to Peers and Tutors comments
  * Updated the project's logfile to gradpad.log
  * Added introduction section to the user guide.
  * Improve test coverage of GradPad. [\#200](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/200)
  
* **Project management**:
  * Recorded comments by Peers and Tutors with regards to GradPad especially on User Guide and Developer Guide.
  * Addressed issues on User Guide from mock PE.
  * Reviews and comments to team mates PR. [Example: \#111](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/111)
  * Managed release for v1.4 on GitHub for submission.

* **Enhancements to existing features**:
  * Refactored the `Delete` feature and command to take in module code as parameters instead of index. [\#120](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/120)
  * Refactored the `Model` package and component of AddressBook into GradPad. [\#55](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/55)
  * Added assertions to `StorageManager`. [\#105](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/105)
  * Added semester data field to `ModuleInfo` class. [\#174](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/174)

* **Documentation**:
  * User Guide:
    * Added Ui Markup for User Guide. [\#52](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/52)
    * Added documentation for the v1.2 feature `delete` [\#23](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/23)
    * Added documentation for the v1.3 feature `search` [\#75](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/75)
    * Added documentation for the v1.3 feature `required` [\#75](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/75)
    * Added introduction and NUSMods section to User Guide. [\#52](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/52) & [\#130](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/130)
    * Updated documentation with comments from CS2101 Tutor and CS2103T TA. [\#130](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/130)
    * Updated command summary in documentation. [\#75](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/75)
    * Updated feature summary list into table form. [\#75](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/75)
    * Updated documentation with issues reported from mock PE for v1.4. [\#172](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/172)
   
  * Developer Guide:
    * Refactored the `Model` component class diagram and description. [\#55](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/55)
    * Added implementation details of the `delete` feature. [\#55](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/55)
    * Refactored the `Undo/Redo` proposed implementation. [\#75](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/75)
    * Added use cases for GradPad in the documentation. [\#75](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/75) & [\#220](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/220)
    * Added the `Appendix C: Effort` section in the documentation. [\#210](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/210)
    * Updated `Manual Testing` section in the documentation. [\#194](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/194)

* **Review/mentoring contributions**:
  * PRs reviewed (with non-trivial review comments): [\#58](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/58), 
  [\#35](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/35), 
  [\#111](https://github.com/AY2021S1-CS2103T-T09-1/tp/pull/111)
  * Assisted teammates with any Git queries or issues, specifically on SourceTree.
  
* **Community**:
  * Assisted in testing and reporting bugs for another team's product:
  [\#1](https://github.com/shaokiat/ped/issues/1),
  [\#2](https://github.com/shaokiat/ped/issues/2),
  [\#3](https://github.com/shaokiat/ped/issues/3),
  [\#4](https://github.com/shaokiat/ped/issues/4),
  [\#5](https://github.com/shaokiat/ped/issues/5)
