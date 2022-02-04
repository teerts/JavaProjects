let hello = "   Hello, World!  ";
let wsRegex = /^(\w+),\s(\w+)$/; // Change this line
let result = hello.replace(wsRegex, ""); // Change this line
console.log(result);