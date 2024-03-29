import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException {
        // Program arguments required 4
        if (args.length < 4) {
            System.out.println("Error: Missing Input");
            System.out.println("Please use jar_app.jar [c|d] [input filepath] [output filepath]");
            System.exit(-1);
        }
        if (!(args[0].equals("huff") || args[0].equals("-huff"))) System.exit(0);

        // Set variables based on args
        char mode = args[1].toLowerCase().charAt(0);
        if (mode == '-') mode = args[1].toLowerCase().charAt(1);
        String inputFilename = args[2];
        String outputFilename = args[3];

        switch (mode) {
            case 'c':
                System.out.println("Encoding...");
                // Open file and read its content, then build a frequency hashmap table and build a huffman tree with it
                FileInputStream fis = new FileInputStream(inputFilename);
                HuffmanCode huffmanCode = new HuffmanCode();
                huffmanCode.buildHuffmanTree(FileReader.loadFileAsCharHashMap(fis));

                // Get a Hash Map of all characters from the tree where Key = Character, String = Huffman code for that character
                Map<Character, String> huffmanMap = new HashMap<>();
                HuffmanCode.buildHuffmanMapFromTree(huffmanMap, huffmanCode.getRootNode(), "");

                // Open file again, this time we are converting the file content to huffman code
                // Text is compressed using hashmap fore greater speed.
                fis = new FileInputStream(inputFilename);
                String huffmanString = HuffmanCode.compress(fis, huffmanMap);

                // save the length of the encoded huffman string so that we know where to truncate when decoding
                huffmanCode.setEncodedLength(huffmanString.length());

                // Write data to file using helper function
                FileWriter.writeHuffmanStringToFile(huffmanString, outputFilename, huffmanCode);
                break;
            case 'd':
                System.out.println("Decoding...");

                // Open file to decompress, The file should be a ArrayList<Object>
                // which contains the huffman code (Tree and String length) at index 0
                // and a byte[] of all the bits previously encoded (the huffman String)
                FileInputStream fisDecompress = new FileInputStream(inputFilename);
                ArrayList<Object> files = FileReader.loadFilesToDecompress(fisDecompress);

                // Load the contents of the list at index 0 in to a HuffmanCode object
                // and index 1 into byte[] Object
                HuffmanCode huffmanCodeDecompressing = (HuffmanCode) files.get(0);
                byte[] huffmanByteArray = (byte[]) files.get(1);

                // Decode the byte[] back into the original text and write to a new text file using helper function
                String decodedHuffmanString = huffmanCodeDecompressing.decode(huffmanByteArray);
                FileWriter.writeTextToFile(decodedHuffmanString, outputFilename);
                break;
            default:
                // If mode incorrect give error and exit.
                System.out.println("Invalid Mode..");
                System.out.println("Please use 'c' for compression and 'd' for decompression");
                System.exit(-1);
                break;
        }
        System.out.println("\nDone!");
    }
}
