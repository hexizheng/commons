@echo off

rem eclipse 工程导入失败，用此命令删除所有工程文件
cd /d %~dp0

for /r . %%a in (.) do (
@if exist "%%a\.classpath" (
echo "delete %%a\.classpath"
del "%%a\.classpath"
)
@if exist "%%a\.project" (
echo "delete %%a\.project"
del "%%a\.project"
)
@if exist "%%a\.settings" (
echo "delete %%a\.settings"
rd /s /q "%%a\.settings"
)
@if exist "%%a\target" (
echo "delete %%a\target"
rd /s /q "%%a\target"
)
@if exist "%%a\bin" (
echo "delete %%a\bin"
rd /s /q "%%a\bin"
)
@if exist "%%a\.svn" (
echo "delete %%a\.svn"
rd /s /q "%%a\.svn"
)
)


echo clean done
pause





