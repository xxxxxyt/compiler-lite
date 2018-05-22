set -e
C_FILE="$1"
BASE_NAME="${C_FILE%.*}"
O_FILE="$BASE_NAME.o"
NASM_FILE="$BASE_NAME.asm"
g++ "$2" -c -o "$O_FILE" "$C_FILE" -std=c++11
./objconv -fnasm "$O_FILE" "$NASM_FILE" -v0
#sed -i 's|st(0)|st0  |g' "$NASM_FILE"
sed -i 's|noexecute|         |g' "$NASM_FILE"
sed -i 's|execute|       |g' "$NASM_FILE"
sed -i 's|: function||g' "$NASM_FILE"
sed -i 's|?_|L_|g' "$NASM_FILE"
sed -i -n '/SECTION .eh_frame/q;p' "$NASM_FILE"
#sed -i 's|;.*||g' "$NASM_FILE"
#sed -i 's/^M//g' "$NASM_FILE"
#sed -i 's|\s\+$||g' "$NASM_FILE"
#sed -i 's|align=1||g' "$NASM_FILE"
sed -i 's|\.LC|LC|g' "$NASM_FILE"


# strange

rm "$O_FILE"
cat $NASM_FILE
#nasm -felf64 main.asm && g++ -O3 main.o && ./a.out

