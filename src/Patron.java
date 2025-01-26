public class Patron {
        private String patronID;
        private String patronName;
        private String address;
        private double overdueFine;

        // Constructor to initialize a patron
        public Patron(String patronID, String patronName, String address, double overdueFine) {
            this.patronID = patronID;
            this.patronName = patronName;
            this.address = address;
            this.overdueFine = overdueFine;
        }

        // Getters
        public String getId() {
            return patronID;
        }

        public String getName() {
            return patronName;
        }

        public String getAddress() {
            return address;
        }

        public double getOverdueFine() {
            return overdueFine;
        }
    //override toString method to display Patron details
        @Override
        public String toString() {
            return patronID + " - " + patronName + " - " + address + " - " + overdueFine;
        }


}

