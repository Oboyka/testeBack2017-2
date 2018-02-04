package br.com.testeBack2017;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * @author Antonio Ribeiro
 *
 */
public class Main {

	//query que será utilizada na criação do preparedStatement.
	private static String query = "";
	
	//Método main
	public static void main(String[] args){
		
		//Gerando uma lista de clientes
		ArrayList<Customer> customers = gerarArrayList();
		
		//Cadastrando os clientes no banco de dados.
		cadastrarClientes(customers);
		
		//Calculando a média dos clientes.
		calcularMedia(560, 1500, 2700);
		
		//Listando os clientes utilizados no cálculo da média.
		listarClientesUtilizados(560, 1500, 2700);
		
	}
	
	/**
	 * Método utlizado para gerar uma lista com os clientes a serem cadastrados.
	 * @return lista de clientes
	 */
	public static ArrayList<Customer> gerarArrayList(){
		
		//lista de clientes.
		ArrayList<Customer> customers = new ArrayList<>();
		
		//Gerando um número n de clientes.
		Customer c = new Customer(45, "56958955966", "Armando Silva", true, 1467.90);
		customers.add(c);
		c = new Customer(356, "58925415699", "Lívia Oliveira", false, 596.60);
		customers.add(c);
		c = new Customer(852, "85296374188", "Paola Nunes", true, 1695.00);
		customers.add(c);
		c = new Customer(1523, "96385274177", "Harvey Specter", true, 3500.00);
		customers.add(c);
		c = new Customer(1625, "12345678966", "Jonnathas Luís", true, 1850.00);
		customers.add(c);
		c = new Customer(1695, "45612378966", "Lucas Santos", true, 420.00);
		customers.add(c);
		c = new Customer(1826, "15948726373", "Luana Falcão", true, 352.50);
		customers.add(c);
		c = new Customer(1965, "35724168939", "Maria Fernanda", false, 1956.90);
		customers.add(c);
		c = new Customer(2356, "26536521452", "Kauany Silva", true, 1658.90);
		customers.add(c);
		c = new Customer(2690, "35265699656", "Gabriela Marx", true, 1956.90);
		customers.add(c);
		c = new Customer(2751, "95123687458", "Chris Blue", false, 582.50);
		customers.add(c);
		c = new Customer(3589, "35412698752", "Julio Santos", true, 452.80);
		customers.add(c);
		c = new Customer(3658, "96374165478", "Gabriel Silva", true, 1542.90);
		customers.add(c);
		
		return customers;
	}
	
	/**
	 * Método utilizado para cadastrar os clientes no banco de dados.
	 * @param customers lista de clientes a serem cadastrados.
	 */
	public static void cadastrarClientes(ArrayList<Customer> customers){
		//query utilizada para persistir os clientes no banco.
		query = "INSERT INTO tb_customer_account VALUES(?,?,?,?,?)";
		
		//persistindo os clientes.
		if(MySQLConnection.getMySQLConnection() != null){
			for(Customer c: customers){
				try {
					PreparedStatement ps = MySQLConnection.getMySQLConnection().prepareStatement(query);
					ps.setInt(1, c.getCustomerId());
					ps.setString(2, c.getCustomerCPFCNPJ());
					ps.setString(3, c.getCustomerName());
					ps.setBoolean(4, c.isCustomerActive());
					ps.setDouble(5, c.getCustomerBalance());
					ps.executeUpdate();
					System.out.println(c.getCustomerName() + " cadastrado com sucesso!");
					ps.close();
				} catch (SQLException e) {
					System.out.println("Ocorreu um erro durante o cadastro de " + c.getCustomerName());
				}
			}
		}
		MySQLConnection.closeConnection();
	}
	
	/**
	 * Método utilizado para calcular a média de saldo.
	 * @param valorTotal Valor utlizado na filtragem de saldos.
	 * @param idInicio Primeiro id de referência para a filtragem.
	 * @param idFim Último id de referência para a filtragem.
	 */
	public static void calcularMedia(double valorTotal, int idInicio, int idFim){
		double result = 0;
		
		if(MySQLConnection.getMySQLConnection() != null){
			try{
				
				//query utilizada para realizar o calculo de média final
				query = "SELECT AVG(vl_total) FROM tb_customer_account WHERE vl_total > ? AND id_customer BETWEEN ? AND ?";
				PreparedStatement ps = MySQLConnection.getMySQLConnection().prepareStatement(query);
				
				ps.setDouble(1, valorTotal);
				ps.setInt(2, idInicio);
				ps.setInt(3, idFim);
				
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					result = rs.getDouble(1);
				}
				
				rs.close();
				ps.close();
				
			}catch(SQLException e){
				e.printStackTrace();
				System.out.println("Houve um erro no cálculo da média total!");
			}
		}
		MySQLConnection.closeConnection();
		
		//retornando a média final
		System.out.println("--------------------------------------------------------");
		System.out.println("Média final: " + result);
	}

	/**
	 * Método que exibe os clientes utilizados no cálculo da média final.
	 * @param valorTotal Valor utlizado na filtragem de saldos.
	 * @param idInicio Primeiro id de referência para a filtragem.
	 * @param idFim Último id de referência para a filtragem.
	 */
	public static void listarClientesUtilizados(double valorTotal, int idInicio, int idFim){
		
		//lista de clientes
		ArrayList<Customer> customers = new ArrayList<>();
		
		if(MySQLConnection.getMySQLConnection() != null){
			
			//query utilizada para buscar os clientes no banco
			query = "SELECT * FROM tb_customer_account WHERE vl_total > ? AND id_customer BETWEEN ? AND ? ORDER BY vl_total DESC";
			
			//buscando os clientes
			try {
				PreparedStatement ps = MySQLConnection.getMySQLConnection().prepareStatement(query);
				ps.setDouble(1, valorTotal);
				ps.setInt(2, idInicio);
				ps.setInt(3, idFim);
				
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					Customer c = new Customer();
					c.setCustomerId(rs.getInt(1));
					c.setCustomerCPFCNPJ(rs.getString(2));
					c.setCustomerName(rs.getString(3));
					c.setCustomerActive(rs.getBoolean(4));
					c.setCustomerBalance(rs.getDouble(5));
					customers.add(c);
				}
				rs.close();
				ps.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MySQLConnection.closeConnection();
		}
		
		//exibindo os clientes
		for(Customer c: customers){
			
			System.out.println("------------------------------------------------------");
			System.out.println("Id: " + c.getCustomerId());
			System.out.println("Nome: " + c.getCustomerName());
			System.out.println("CPF/CNPJ: " + c.getCustomerCPFCNPJ());
			System.out.println("Saldo: " + c.getCustomerBalance());
			if(c.isCustomerActive()){
				System.out.println("Status: ATIVO");
			}else{
				System.out.println("Status: INATIVO");
			}
			
		}
		
	}
	
}
