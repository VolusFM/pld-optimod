package tsp;

public class MainTSP {

	public static void main(String[] args) {
		int[][] graph = {
				/* v---- 0, 1, 2, 3, 4 */
				/* 0 */ {0, 2, 3, 4, 5},
				/* 1 */ {3, 0, 4, 8, 2},
				/* 2 */ {1, 2, 0, 5, 3},
				/* 3 */ {4, 5, 3, 0, 3},
				/* 4 */ {4, 5, 6, 5, 0},
				};
		
		int[] duree = // TODO : je ne comprend pas comment c'est utilisé
				/* 0, 1, 2, 3, 4 */
				  {0, 0, 0, 0, 0};
		
		int nbSommets = 5;

		
		chercherEtAfficherMeilleureSolution(0, nbSommets, graph, duree);
	}
	
	private static void chercherEtAfficherMeilleureSolution(int tempsMaxMs, int nbSommets, int[][] graph, int[] duree) {
		TSP tsp = new TSP1();
		tsp.chercheSolution(0, nbSommets, graph, duree);
		
		
		if (tsp.getTempsLimiteAtteint()) {
			System.err.println("Temps insuffisant pour trouver la meilleure solution");
			return;
		}
		
		System.out.println("Coût de la meilleure solution : " + tsp.getCoutMeilleureSolution());
		System.out.println();
		System.out.println("Meilleure solution : ");
		int sommetCourant = 0;
		int sommetInitial = 0;
		int sommetSuivant;
		int i;
		for (i = 1; i < nbSommets; i++) {
			System.out.println("Étape " + i + " :");
			sommetSuivant = tsp.getMeilleureSolution(i);
			System.out.println("Aller de " + sommetCourant + " à " + sommetSuivant + " (pour un coût de " + graph[sommetCourant][sommetSuivant] + ")");
			sommetCourant = sommetSuivant;
			System.out.println();

		}
		System.out.println("Étape " + ++i + " (finale) :");
		System.out.println("Aller de " + sommetCourant + " à " + sommetInitial + " (pour un coût de " + graph[sommetCourant][sommetInitial] + ")");
	}
	
	
}
