set commands-dir=%CD%
cd %UserProfile%\Documents
mkdir CRMTool
xcopy /s %commands-dir%\db-commands.txt %UserProfile%\Documents\CRMTool
cd CRMTool
sqlite3 customers.db < db-commands.txt