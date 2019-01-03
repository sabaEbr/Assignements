#include <stdlib.h>
#include <stdio.h>

int main() {
    printf("%s\n", "building...");
    // res:src on linux
    system("cd .. && javac -d output -sourcepath src src/com/GO/game/GameLauncher.java");
    printf("%s\n\n", "Done! - building");

    return 0;
}