#include <stdlib.h>
#include <stdio.h>

int main() {
    printf("%s\n", "building...");
    // res:src on linux
    system("cd .. && java -cp output com/zerulus/game/GameLauncher");
    printf("%s\n\n", "Done! - building");

    return 0;
}