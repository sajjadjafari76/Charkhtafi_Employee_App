package ir.stit.ir.charkhtafi.Model;



public class EditPriceModel {

        private String Id;
        private String Product_Id;
        private String Name;
        private String Price;
        private String Image;
        private String Type;

        public String getId() {
                return Id;
        }

        public void setId(String id) {
                Id = id;
        }

        public String getName() {
                return Name;
        }

        public void setName(String name) {
                Name = name;
        }

        public String getPrice() {
                return Price;
        }

        public void setPrice(String price) {
                Price = price;
        }

        public String getImage() {
                return Image;
        }

        public void setImage(String image) {
                Image = image;
        }

        public String getType() {
                return Type;
        }

        public void setType(String type) {
                Type = type;
        }

        public String getProduct_Id() {
                return Product_Id;
        }

        public void setProduct_Id(String product_Id) {
                Product_Id = product_Id;
        }
}
