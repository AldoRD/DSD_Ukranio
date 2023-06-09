$filePath = ".\Ramirez_Dominguez_Aldo_Eduado_4CM14.txt"
if (Test-Path $filePath) { Remove-Item $filePath }
for ($i = 1; $i -le 24; $i++) {
    $folder = ".\Ejercicio$i"
    Get-ChildItem -Path $folder -Filter *.java -Recurse | ForEach-Object {
        Get-Content $_.FullName | Out-File -Append $filePath
        "`n*********************************************************************`n" | Out-File -Append $filePath
    }
}

