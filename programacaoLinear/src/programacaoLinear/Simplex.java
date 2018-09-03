package programacaoLinear;

public class Simplex {
   private static double [][] tabela;
   private static int linha;
   private static int coluna;
	
public static void main(String[] args) {
		
		double[][] Restrica={{0.5,0,1,8},{0,1,1,8},{1,2,1,24}};
		double[]func={40,30};
		try{
			
		montarMatriz(2, 3, Restrica, func);
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("POR FAVOR, VERIFICAR OS VALORES INFORMADOS.EXISTE INCONSISTÊNCIA NOS ITENS INFORMADOS!!!");	
		}
	}
	
	
	public static double[][] montarMatriz(int NumVarDecisao,int numRestricao, double[][]MatrizRestricao,double[]FObjetiva)throws ArrayIndexOutOfBoundsException{
		 linha=numRestricao+1;
		 coluna=NumVarDecisao+numRestricao+1;
		 tabela=new double[linha][coluna];
		
		//Preencher tabela com Zero
		for(int i=0;i<linha;i++){
			for(int j=0;j<coluna;j++) tabela[i][j]=0;
		}
		
		//Preencher última linha com valores da função objetiva 
		
		for(int i=0;i<NumVarDecisao;i++) tabela[numRestricao][i]=FObjetiva[i]*(-1);
			
		
		for(int i=0;i<numRestricao;i++){
			tabela[i][coluna-1]=MatrizRestricao[i][NumVarDecisao+1];  //preencher ultima coluna com os valores da última coluna da MatrizRestrição
			for(int j=0;j<coluna-1;j++) {
				if(j==NumVarDecisao) tabela[i][j+i]=MatrizRestricao[i][j]; // Insere os coeficientes das variaveis de folgas
				else if(j<numRestricao)tabela[i][j]=MatrizRestricao[i][j]; // Insere os coeficientes das variaveis de decisão
			}
		}
		
		for(int i=0;i<linha;i++){
			for(int j=0;j<coluna;j++){ System.out.print(tabela[i][j]+" ");}
			System.out.println();
		}
		
		System.out.println(VerificarSeOtimo());
		
		return tabela;
	}
	
	public static boolean VerificarSeOtimo(){
        boolean Otimo = false;
        int Count = 0;
        for(int i = 0; i < coluna; i++){
            double val = tabela[linha-1][i];
            if(val >= 0){
                Count++;
            }
        }
      
        if(Count == coluna){
            Otimo = true;
        }
        
        return Otimo;
    }

}

