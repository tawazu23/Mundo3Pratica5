package cadastroserver;

/**
 *
 * @author ruanf
 */

import controller.MovimentacaoJpaController;
import controller.PessoaJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class CadastroServer {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroServerPU");
        ProdutoJpaController ctrlProduto = new ProdutoJpaController(emf);
        UsuarioJpaController ctrlUsuario = new UsuarioJpaController(emf);
        MovimentacaoJpaController ctrlMovimento = new MovimentacaoJpaController(emf);
        PessoaJpaController ctrlPessoa = new PessoaJpaController(emf);
        ServerSocket servidorSocket = null;
        try {
            servidorSocket = new ServerSocket(12345);
            System.out.println("Server aguardando conexões ...");
            while (true) {
                Socket clienteSocket = servidorSocket.accept();
                CadastroThread thread = new CadastroThread(ctrlProduto, ctrlUsuario, ctrlMovimento, ctrlPessoa, clienteSocket);
                thread.start();
            }
        } catch (IOException e) {
        } finally {
            if (servidorSocket != null && !servidorSocket.isClosed()) {
                try {
                    servidorSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}