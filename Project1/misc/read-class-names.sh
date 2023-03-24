extension=$1
input=
for ((i = 1; i <=$2; i++)); do
   read input
   str=$(basename ${input} ${extension})
   echo "${str}"
done
