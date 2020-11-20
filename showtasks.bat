call runcrud.bat
if "%ERRORLEVEL%" == "0" goto next
echo runcrud.bat failed.
goto fail

:next
start chrome http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.