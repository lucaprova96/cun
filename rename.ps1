$rootDir = "c:\Users\Luca Drogo\Desktop\Nuova cartella\LUCE"

# 1. Replace package name in all text files
$files = Get-ChildItem -Path $rootDir -Recurse -File -Include *.xml,*.kt,*.java,*.gradle,*.properties,*.json

foreach ($file in $files) {
    # Skip .git directory and build directories
    if ($file.FullName -match "\\\.git\\" -or $file.FullName -match "\\build\\") { continue }
    
    $content = Get-Content $file.FullName -Raw
    if ($null -ne $content) {
        $newContent = $content -replace "com\.streamflixreborn\.streamflix", "com.luce"
        $newContent = $newContent -replace "Streamflix", "Luce"
        $newContent = $newContent -replace "StreamFlix", "Luce"
        
        if ($content -cne $newContent) {
            Set-Content -Path $file.FullName -Value $newContent -NoNewline
        }
    }
}

# 2. Move directory structure
$srcDirs = @(
    "$rootDir\app\src\main\java",
    "$rootDir\app\src\tv\java",
    "$rootDir\app\src\test\java",
    "$rootDir\app\src\androidTest\java"
)

foreach ($srcDir in $srcDirs) {
    $oldPath = "$srcDir\com\streamflixreborn\streamflix"
    $newPath = "$srcDir\com\luce"
    
    if (Test-Path $oldPath) {
        New-Item -ItemType Directory -Force -Path $newPath | Out-Null
        Move-Item -Path "$oldPath\*" -Destination $newPath -Force
        Remove-Item "$srcDir\com\streamflixreborn\streamflix" -Force -Recurse -ErrorAction SilentlyContinue
        Remove-Item "$srcDir\com\streamflixreborn" -Force -Recurse -ErrorAction SilentlyContinue
    }
}

Write-Host "Rename complete"
