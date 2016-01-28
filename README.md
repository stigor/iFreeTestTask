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

Then just launch runTests.cmd from bin folder:

```bash
runTests.cmd
```

Results will be in testResults.txt file near runTests.cmd.


## ATTENTION
If you use Firefox browser do not change focus from browser window when script works.
