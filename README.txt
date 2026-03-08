Buatlah program (Java & Python) untuk mengevaluasi sebuah ekspresi aritmatika dengan menggunakan struktur data Stack (boleh Array/Linked List), dengan ketentuan sebagai berikut:

1. Input merupakan sebuah ekspresi aritmatika (infix)
2. Output meliputi:
-postfix expression, 
-step by step perhitungan (berdasarkan proses push-pop stack), dan
-hasil akhir perhitungan

Prompt yang saya gunakan:

Perbaiki code berikut (jika ada bug) tanpa mengubah fungsi utama nya
def precedence(op):
    if op in ['+', '-']:
        return 1
    if op in ['*', '/']:
        return 2
    return 0


def infix_to_postfix(expression):
    stack = []
    postfix = []
    number = ""

    print("\nProses Push-Pop Stack (Infix -> Postfix):")

    for c in expression:

        if c.isdigit():
            number += c

        else:
            if number:
                postfix.append(number)
                print("Output:", " ".join(postfix))
                number = ""

            if c == '(':
                stack.append(c)
                print("Push:", c)

            elif c == ')':
                while stack and stack[-1] != '(':
                    postfix.append(stack.pop())
                    print("Pop -> Output:", " ".join(postfix))
                stack.pop()

            elif c in "+-*/":
                while stack and stack[-1] != '(' and precedence(stack[-1]) >= precedence(c):
                    postfix.append(stack.pop())
                    print("Pop -> Output:", " ".join(postfix))

                stack.append(c)
                print("Push:", c)

    if number:
        postfix.append(number)
        print("Output:", " ".join(postfix))

    while stack:
        postfix.append(stack.pop())
        print("Pop -> Output:", " ".join(postfix))

    return postfix


def evaluate_postfix(postfix):
    stack = []

    print("\nStep Evaluasi Postfix:")

    for token in postfix:

        if token.isdigit():
            stack.append(int(token))
            print("Push:", token)

        else:
            b = stack.pop()
            a = stack.pop()

            if token == '+':
                result = a + b
            elif token == '-':
                result = a - b
            elif token == '*':
                result = a * b
            elif token == '/':
                result = a / b

            stack.append(result)
            print("Pop", a, "dan", b, "-> Push hasil:", result)

    return stack.pop()


# MAIN
infix = input("Masukkan ekspresi infix: ")

postfix = infix_to_postfix(infix)

print("\nPostfix Expression:", " ".join(postfix))

hasil = evaluate_postfix(postfix)

print("\nHasil Akhir:", hasil)

Java:
import java.util.Stack;
import java.util.Scanner;

public class EvaluasiStack {

    // Menentukan prioritas operator
    static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    // Konversi infix ke postfix
    static String infixToPostfix(String exp) {
        Stack<Character> stack = new Stack<>();
        String postfix = "";

        System.out.println("\nProses Push-Pop Stack (Konversi Infix -> Postfix):");

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (Character.isDigit(c)) {
                postfix += c;
                System.out.println("Output: " + postfix);
            }
            else if (c == '(') {
                stack.push(c);
                System.out.println("Push: " + c);
            }
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix += stack.pop();
                    System.out.println("Pop -> Output: " + postfix);
                }
                stack.pop();
            }
            else {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    postfix += stack.pop();
                    System.out.println("Pop -> Output: " + postfix);
                }
                stack.push(c);
                System.out.println("Push: " + c);
            }
        }

        while (!stack.isEmpty()) {
            postfix += stack.pop();
            System.out.println("Pop -> Output: " + postfix);
        }

        return postfix;
    }

    // Evaluasi postfix
    static int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();

        System.out.println("\nStep Evaluasi Postfix:");

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);

            if (Character.isDigit(c)) {
                stack.push(c - '0');
                System.out.println("Push: " + (c - '0'));
            } else {
                int b = stack.pop();
                int a = stack.pop();

                int result = 0;

                switch (c) {
                    case '+': result = a + b; break;
                    case '-': result = a - b; break;
                    case '*': result = a * b; break;
                    case '/': result = a / b; break;
                }

                stack.push(result);
                System.out.println("Pop " + a + " dan " + b + " -> Push hasil: " + result);
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan ekspresi infix: ");
        String infix = input.nextLine();

        String postfix = infixToPostfix(infix);

        System.out.println("\nPostfix Expression: " + postfix);

        int hasil = evaluatePostfix(postfix);

        System.out.println("\nHasil Akhir: " + hasil);
    }
}

AI dan Web yang digunakan: Claude untuk debugging, ChatGPT untuk asisten, GeeksForGeeks sebagai sumber