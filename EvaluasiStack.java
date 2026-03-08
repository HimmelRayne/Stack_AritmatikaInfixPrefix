import java.util.Stack;
import java.util.Scanner;

public class EvaluasiStack {

    static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    static String infixToPostfix(String exp) {
        Stack<Character> stack = new Stack<>();
        // BUG FIX 4: Pakai StringBuilder, bukan String += (lebih efisien)
        StringBuilder postfix = new StringBuilder();

        System.out.println("\nProses Push-Pop Stack (Konversi Infix -> Postfix):");

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            // BUG FIX 2: Skip spasi agar "3 + 4" tetap valid
            if (c == ' ') continue;

            // BUG FIX 1: Tangani angka multi-digit (misal: 12, 100)
            // Baca semua digit berurutan, gabung jadi satu token dengan separator spasi
            if (Character.isDigit(c)) {
                StringBuilder number = new StringBuilder();
                while (i < exp.length() && Character.isDigit(exp.charAt(i))) {
                    number.append(exp.charAt(i));
                    i++;
                }
                i--; // Kembalikan index karena loop for akan i++ lagi

                postfix.append(number).append(" ");
                System.out.println("Output: " + postfix.toString().trim());
            }
            else if (c == '(') {
                stack.push(c);
                System.out.println("Push: " + c);
            }
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()).append(" ");
                    System.out.println("Pop -> Output: " + postfix.toString().trim());
                }
                stack.pop(); // Buang '('
            }
            else if (c == '+' || c == '-' || c == '*' || c == '/') {
                // BUG FIX 3: Tambah guard stack.peek() != '(' agar '(' tidak di-pop
                // saat membandingkan precedence
                while (!stack.isEmpty() && stack.peek() != '(' && precedence(stack.peek()) >= precedence(c)) {
                    postfix.append(stack.pop()).append(" ");
                    System.out.println("Pop -> Output: " + postfix.toString().trim());
                }
                stack.push(c);
                System.out.println("Push: " + c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(" ");
            System.out.println("Pop -> Output: " + postfix.toString().trim());
        }

        return postfix.toString().trim();
    }

    // BUG FIX 1 (lanjutan): evaluatePostfix sekarang baca token per token (split spasi)
    // karena postfix sudah berformat "12 3 +" bukan "123+"
    static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();

        System.out.println("\nStep Evaluasi Postfix:");

        String[] tokens = postfix.split(" ");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            // Cek apakah token adalah angka (bukan operator)
            try {
                double num = Double.parseDouble(token);
                stack.push(num);
                System.out.println("Push: " + token);
            } catch (NumberFormatException e) {
                double b = stack.pop();
                double a = stack.pop();
                double result = 0;

                switch (token.charAt(0)) {
                    case '+': result = a + b; break;
                    case '-': result = a - b; break;
                    case '*': result = a * b; break;
                    case '/':
                        // BUG FIX 5: Tambah pengecekan division by zero
                        if (b == 0) throw new ArithmeticException("Error: Pembagian dengan nol!");
                        result = a / b;
                        break;
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

        double hasil = evaluatePostfix(postfix);

        // Tampilkan sebagai int jika hasilnya bilangan bulat (misal 6.0 -> 6)
        if (hasil == (long) hasil) {
            System.out.println("\nHasil Akhir: " + (long) hasil);
        } else {
            System.out.println("\nHasil Akhir: " + hasil);
        }

        input.close();
    }
}