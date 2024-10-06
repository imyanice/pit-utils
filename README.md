# Pit Utils
This is a Weave mod. 
1. Download Weave Manager https://github.com/Weave-MC/Weave-Manager
2. Downgrade Weave:
<details>
  <summary>Mac/Linux</summary>
  
  ```shell
cd ~/.weave
rm -f loader.jar
rm -f Weave-Loader-Agent-0.2.6.jar
rm -f Weave-Loader-Agent-0.2.6.jar.1
wget https://github.com/Weave-MC/Weave-Loader/releases/download/v0.2.6/Weave-Loader-Agent-0.2.6.jar
mv Weave-Loader-Agent-0.2.6.jar loader.jar
echo "{\"auto_update\":false,\"ignore_updates\":true,\"startup_run\":false,\"compact_buttons\":false,\"theme\":\"Cat Mocha\",\"loader_version\":\"v0.2.6\"}" > manager.settings
```
  
</details>

<details>
  <summary>Windows</summary>
  
  ```shell
cd %userprofile%/.weave
del "loader.jar"
powershell -Command "Invoke-WebRequest https://github.com/Weave-MC/Weave-Loader/releases/download/v0.2.6/Weave-Loader-Agent-0.2.6.jar -OutFile loader.jar"
set settings={"auto_update":false,"ignore_updates":true,"startup_run":false,"compact_buttons":false,"theme":"Cat Mocha","loader_version":"v0.2.6"}
echo %settings% > manager.settings
pause
```
  
</details>

3. Download Pit-Utils and put it in your weave mod's folder
4. Launch a Minecraft instance with Weave

# License GPL-v3.0
