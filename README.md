iFreeTestTask
=============
Test task from iFree company.
Need to create automatic tests (Selenium+Java) for "Add New App" dialog on this [page](http://ui.moneytapp.vas61t.test.i-free.ru/webui-rc/apps/list)

## Requirements

* Windows. Tested on Windows 10
* JRE 1.8
* Installed Chrome OR Firefox browser


## Usage
Edit bin\runTests.cmd file.
Set correct login, password and base URL if required.

<<<<<<< HEAD
Then just launch runTests.cmd from bin folder:
=======
Then just launch runTests.cmd:
>>>>>>> 75945e3bb9089d58b4075d1ad3e9213e7983e654

```bash
runTests.cmd
```

Results will be in testResults.txt file near runTests.cmd.


## ATTENTION
If you use Firefox browser do not change focus from browser window when script works.
