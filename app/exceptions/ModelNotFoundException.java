package exceptions;

public class ModelNotFoundException extends Exception {

    public ModelNotFoundException(Class modelClass) {
        super((String)modelClass.getName() + " not found");
    }
}
