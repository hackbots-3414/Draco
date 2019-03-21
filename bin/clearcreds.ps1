
# Copy/paste this into a powershell terminal:
cmdkey /list | ForEach-Object{if($_ -like "*Target:*" -and $_ -like "*github*"){cmdkey /del:($_ -replace " ","" -replace "Target:","")}}

# Note that script execution is typically disabled, so this must be run manually.
