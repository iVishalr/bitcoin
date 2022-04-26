package bitcoin;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import ecc.Curve;
import ecc.Point;
import ecc.Generator;
import ecc.PublicKey;
import transaction.TxIn;
// import scraper.ParseBlockChain;
import transaction.TxOut;
import transaction.Script;
import transaction.Tx;
import ecdsa.Signature;

public class BTC{
  public static String toHex(String arg) {
    return String.format("%040x", new BigInteger(1, arg.getBytes(Charset.forName("UTF-8"))));
  }

  private static String bytesToHex(byte[] in) {
    final StringBuilder builder = new StringBuilder();
    for(byte b : in) {
        builder.append(String.format("%02x", 0xFF & b));
    }
    return builder.toString();
  }
  public static void main(String []args) throws Exception{

    BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16);
    BigInteger a = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000", 16);
    BigInteger b = new BigInteger("0000000000000000000000000000000000000000000000000000000000000007", 16);
    BigInteger x = new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798", 16);
    BigInteger y = new BigInteger("483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8", 16);
    BigInteger n = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16);
    
    Curve bitcoinCurve = new Curve(p,a,b);
    Point G = new Point(bitcoinCurve,x,y);
    Generator bitcoinGenerator = new Generator(G, n);

    System.out.println("Curve Parameters : \n"+bitcoinCurve.toString());
    System.out.println("Seed Point parameters : \n"+G.toString());
    System.out.println("Generator parameters : \n"+bitcoinGenerator.toString());
    System.out.println("Generator Point is on curve : " + G.verify_on_curve());
    
    int sk1 = 1;
    Point pk1 = G;
    System.out.println("Secret Key : "+sk1+"\nPublic Key : \nx : "+pk1.x+"\ny : "+pk1.y);
    System.out.println("Generated Point is on curve : " + pk1.verify_on_curve());
    System.out.println("-------------------------------------------------------------------");
    int sk2 = 2;
    
    long startTime = System.nanoTime();
    Point pk2 = G.add(G);
    long endTime = System.nanoTime();
    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds
    System.out.println("Secret Key : "+sk2+"\nPublic Key : \nx : "+pk2.x+"\ny : "+pk2.y);
    System.out.println("Generated Point is on curve : " + pk2.verify_on_curve());
    System.out.println("Time : "+duration/1000000+" ms");
    System.out.println("-------------------------------------------------------------------");
    int sk3 = 3;

    startTime = System.nanoTime();
    Point pk3 = G.add(G).add(G);
    endTime = System.nanoTime();
    duration = (endTime - startTime);
    
    System.out.println("Secret Key : "+sk3+"\nPublic Key : \nx : "+pk3.x+"\ny : "+pk3.y);
    System.out.println("Generated Point is on curve : " + pk3.verify_on_curve());
    System.out.println("Time : "+duration/1000000+" ms");
    System.out.println("-------------------------------------------------------------------\n");

    int msk1 = 1;
    Point mpk1 = G;
    System.out.println("Secret Key : "+msk1+"\nPublic Key : \nx : "+mpk1.x+"\ny : "+mpk1.y);
    System.out.println("Generated Point is on curve : " + mpk1.verify_on_curve());
    System.out.println("-------------------------------------------------------------------");

    int msk2 = 2;
    startTime = System.nanoTime();
    Point mpk2 = G.multiply(new BigInteger("2"));
    endTime = System.nanoTime();
    duration = (endTime - startTime);
    
    System.out.println("Secret Key : "+msk2+"\nPublic Key : \nx : "+mpk2.x+"\ny : "+mpk2.y);
    System.out.println("Generated Point is on curve : " + mpk2.verify_on_curve());
    System.out.println("Time : "+duration/1000000+" ms");
    System.out.println("-------------------------------------------------------------------");

    int msk3 = 3;
    startTime = System.nanoTime();
    Point mpk3 = G.multiply(new BigInteger("3"));
    endTime = System.nanoTime();
    duration = (endTime - startTime);
    
    System.out.println("Secret Key : "+msk3+"\nPublic Key : \nx : "+mpk3.x+"\ny : "+mpk3.y);
    System.out.println("Generated Point is on curve : " + mpk3.verify_on_curve());
    System.out.println("Time : "+duration/1000000+" ms");
    System.out.println("-------------------------------------------------------------------");

    String secretKey_string = "Andrej is cool :P";
    BigInteger secretKey = new BigInteger(toHex(secretKey_string),16);
    startTime = System.nanoTime();
    Point publicKey = G.multiply(secretKey);
    endTime = System.nanoTime();
    duration = (endTime - startTime);
    System.out.println("Secret Key : "+secretKey_string);
    System.out.println("Public Key : \nx : "+publicKey.x+"\ny : "+publicKey.y);
    System.out.println("Public Key generated is on curve : \033[92m" + publicKey.verify_on_curve()+"\033[0m");
    System.out.println("Time : "+duration/1000000+" ms");
    System.out.println("-------------------------------------------------------------------");

    String secretKey_string2 = "Andrej's Super Secret 2nd Wallet";
    BigInteger secretKey2 = new BigInteger(toHex(secretKey_string2),16);
    startTime = System.nanoTime();
    Point publicKey2 = G.multiply(secretKey2);
    endTime = System.nanoTime();
    duration = (endTime - startTime);
    System.out.println("Secret Key : "+secretKey_string2);
    System.out.println("Public Key : \nx : "+publicKey2.x+"\ny : "+publicKey2.y);
    System.out.println("Public Key generated is on curve : \033[92m" + publicKey2.verify_on_curve()+"\033[0m");
    System.out.println("Time : "+duration/1000000+" ms");
    System.out.println("-------------------------------------------------------------------");

    startTime = System.nanoTime();
    String pbk1 = PublicKey.toPublicKey(publicKey).address("test", true);
    endTime = System.nanoTime();
    duration = (endTime - startTime);
    
    System.out.println("\nBitcoin addr : "+pbk1);
    System.out.println("Length of addr : "+pbk1.length());
    System.out.println("Time Taken to Generate : "+duration/1000000+" ms");
    System.out.println("-------------------------------------------------------------------");

    startTime = System.nanoTime();
    String pbk2 = PublicKey.toPublicKey(publicKey2).address("test", true);
    endTime = System.nanoTime();
    duration = (endTime - startTime);
    
    System.out.println("\nBitcoin addr : "+pbk2);
    System.out.println("Length of addr : "+pbk2.length());
    System.out.println("Time Taken to Generate : "+duration/1000000+" ms");
    System.out.println("-------------------------------------------------------------------\n");

    BigInteger transaction_id = new BigInteger("46325085c89fb98a4b7ceee44eac9b955f09e1ddc86d8dad3dfdcba46b4d36b2", 16);
    TxIn tx_in = new TxIn(transaction_id.toByteArray(), 1, null, "test");
    
    byte[] out1_pkb_hash = PublicKey.toPublicKey(publicKey2).encode(true, true);
    ArrayList<Object> t = new ArrayList<Object>();
    ArrayList<ArrayList<Object>> temp = new  ArrayList<ArrayList<Object>>();
    t.add(118);
    temp.add(t);
    t = new ArrayList<Object>();
    t.add(169);
    temp.add(t);
    t = new ArrayList<Object>();
    for(byte by : out1_pkb_hash){
        t.add((byte)by);
    }
    // System.out.println(Arrays.toString(t.toArray()));
    temp.add(t);
    t = new ArrayList<Object>();
    t.add(136);
    temp.add(t);
    t = new ArrayList<Object>();
    t.add(172);
    temp.add(t);
    
    Script out1_script = new Script(temp);
    System.out.println(bytesToHex(out1_script.encode()));

    byte[] out2_pkb_hash = PublicKey.toPublicKey(publicKey).encode(true, true);
    ArrayList<Object> t2 = new ArrayList<Object>();
    ArrayList<ArrayList<Object>> temp2 = new  ArrayList<ArrayList<Object>>();
    t2.add(118);
    temp2.add(t2);
    t2 = new ArrayList<Object>();
    t2.add(169);
    temp2.add(t2);
    t2 = new ArrayList<Object>();
    for(byte by : out2_pkb_hash){
        t2.add((byte)by);
    }
    // System.out.println(Arrays.toString(t.toArray()));
    temp2.add(t2);
    t2 = new ArrayList<Object>();
    t2.add(136);
    temp2.add(t2);
    t2 = new ArrayList<Object>();
    t2.add(172);
    temp2.add(t2);
    Script out2_script = new Script(temp2);
    System.out.println(bytesToHex(out2_script.encode()));
    
    TxOut tx_out1 = new TxOut(50000, out1_script);
    TxOut tx_out2 = new TxOut(47500, out2_script);

    ArrayList<TxOut> out_scripts = new ArrayList<TxOut>();
    out_scripts.add(tx_out1);
    out_scripts.add(tx_out2);
    
    ArrayList<TxIn> tx_in_scripts = new ArrayList<TxIn>();
    tx_in_scripts.add(tx_in);

    Tx tx = new Tx(1, tx_in_scripts, out_scripts);

    ArrayList<Object> t3 = new ArrayList<Object>();
    ArrayList<ArrayList<Object>> temp3 = new  ArrayList<ArrayList<Object>>();
    t3.add(118);
    temp3.add(t3);
    t3 = new ArrayList<Object>();
    t3.add(169);
    temp3.add(t3);
    t3 = new ArrayList<Object>();
    for(byte by : out2_pkb_hash){
        t3.add((byte)by);
    }
    // System.out.println(Arrays.toString(t.toArray()));
    temp3.add(t3);
    t3 = new ArrayList<Object>();
    t3.add(136);
    temp3.add(t3);
    t3 = new ArrayList<Object>();
    t3.add(172);
    temp3.add(t3);

    Script source_script = new Script(temp3);
    System.out.println("recall out2_pkb_hash is just raw bytes of the hash of public_key: "+bytesToHex(out2_pkb_hash));
    System.out.println(bytesToHex(source_script.encode()));

    TxIn.setPrevScript(tx_in, source_script);
    byte[] message = tx.encode(true, 0);
    System.out.println("\nmessage : "+bytesToHex(message));
    System.out.println("-------------------------------------------------------------------\n");

    Signature sig = new Signature();
    sig = sig.sign(secretKey, message);
    System.out.println("\nSignature : \n" + sig);
    System.out.println("-------------------------------------------------------------------\n");

    byte[] sig_bytes = sig.encode();
    System.out.println(bytesToHex(sig_bytes));

    byte[] sig_bytes_and_type = new byte[sig_bytes.length+1];
    sig_bytes_and_type[sig_bytes.length] = (byte)0x01;

    byte[] pubkey_bytes = PublicKey.toPublicKey(publicKey).encode(true, false);

    ArrayList<ArrayList<Object>> par = new ArrayList<ArrayList<Object>>();
    t3 = new ArrayList<Object>();
    for(byte sbt : sig_bytes_and_type){
      t3.add(sbt);
    }
    par.add(t3);
    t3 = new ArrayList<Object>();
    for(byte sbt : pubkey_bytes){
      t3.add(sbt);
    }
    par.add(t3);
    Script script_sig = new Script(par);
    TxIn.setScript(tx_in, script_sig);
    
    // Object re[] = PublicKey.gen_key_pair();
    // System.out.println(re[0]);
    // System.out.println(re[1]);
    // ParseBlockChain e = new ParseBlockChain();
    // e.get();

  
  }
}


