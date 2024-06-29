package refactoring.nms.resource;

public class ResourceMain {

    public static void main(String[] args) {

        args = "127.0.0.1, 2, mbk1991, netCD, stateCD, devGubun, loginUser "
                .split(",");

        ResourceProcessor processor = (args.length > 0)?
                new ResourceRegister(args) : new ResourceUpdater();


        processor.process();

    }
}
