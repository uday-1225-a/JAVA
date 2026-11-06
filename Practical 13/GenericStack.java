class ArrayStack<T> {
    private Object[] elements;
    private int top;
    private int capacity;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[capacity];
        this.top = -1;
    }

    public void push(T item) {
        if (top == capacity - 1) {
            throw new IllegalStateException("Stack Overflow: Cannot push onto full stack!");
        }
        elements[++top] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            System.out.println("Warning: Attempted pop() on an empty stack!");
            return null;
        }
        T item = (T) elements[top];
        elements[top--] = null; // Prevent memory leak
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            System.out.println("Warning: Attempted peek() on an empty stack!");
            return null;
        }
        return (T) elements[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }
}

public class GenericStack {
    public static void main(String[] args) {
        System.out.println("=== 1. Generic Stack for Strings ===");
        ArrayStack<String> bookStack = new ArrayStack<>(5);
        bookStack.push("Java in a Nutshell");
        bookStack.push("Effective Java");
        bookStack.push("Clean Architecture");

        System.out.println("Top book (peek): " + bookStack.peek());
        System.out.println("Stack size: " + bookStack.size());

        System.out.println("Popped: " + bookStack.pop());
        System.out.println("Popped: " + bookStack.pop());
        System.out.println("Popped: " + bookStack.pop());
        
        // Empty case handling
        System.out.println("Attempting pop on empty book stack:");
        String emptyPop = bookStack.pop();
        System.out.println("Returned value: " + emptyPop);

        System.out.println("\n=== 2. Reusing Generic Stack for Integers ===");
        ArrayStack<Integer> numberStack = new ArrayStack<>(3);
        numberStack.push(10);
        numberStack.push(20);
        numberStack.push(30);

        while (!numberStack.isEmpty()) {
            System.out.println("Popped integer: " + numberStack.pop());
        }
    }
}

