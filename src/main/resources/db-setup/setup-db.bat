if not exist %UserProfile%\Documents\CRMTool
set commands-dir=%CD%
cd %UserProfile%\Documents
mkdir CRMTool
move %commands-dir%\db-commands.txt %UserProfile%\Documents\CRMTool
cd CRMTool
sqlite3 customers.db < db-commands.txt