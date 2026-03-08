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
        # BUG FIX 1: Abaikan spasi agar ekspresi seperti "3 + 4" tetap valid
        if c == ' ':
            continue

        if c.isdigit() or c == '.':  # BUG FIX 2: Tambah support angka desimal
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
                stack.pop()  # Buang '('

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
        # BUG FIX 3: Ganti token.isdigit() -> try/except float()
        # isdigit() gagal untuk angka desimal ("3.5") dan angka negatif ("-3")
        try:
            stack.append(float(token))
            print("Push:", token)

        except ValueError:
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

    final = stack.pop()
    # BUG FIX 4: Tampilkan int jika hasilnya bilangan bulat (misal 6.0 -> 6)
    return int(final) if final == int(final) else final


# MAIN
infix = input("Masukkan ekspresi infix: ")

postfix = infix_to_postfix(infix)

print("\nPostfix Expression:", " ".join(postfix))

hasil = evaluate_postfix(postfix)

print("\nHasil Akhir:", hasil)