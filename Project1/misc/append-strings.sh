firststr=$1
input=
for ((i = 1; i <=$2; i++)); do
   read input
   echo "${firststr}${input}"
done
