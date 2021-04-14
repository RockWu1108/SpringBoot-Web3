package com.example.web3api;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class Shop extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50600080546001600160a01b031916331790556108b7806100326000396000f3fe6080604052600436106100705760003560e01c8063780bd3f11161004e578063780bd3f1146100f6578063c8691b2a146101a6578063da96832a1461024f578063fca56d401461030b57610070565b80631aa3a0081461007557806322fdef941461008c57806353b62866146100d3575b600080fd5b34801561008157600080fd5b5061008a610344565b005b34801561009857600080fd5b506100bf600480360360208110156100af57600080fd5b50356001600160a01b0316610380565b604080519115158252519081900360200190f35b61008a600480360360408110156100e957600080fd5b5080359060200135610395565b34801561010257600080fd5b506101206004803603602081101561011957600080fd5b503561055d565b6040518080602001848152602001838152602001828103825285818151815260200191508051906020019080838360005b83811015610169578181015183820152602001610151565b50505050905090810190601f1680156101965780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b3480156101b257600080fd5b506101d0600480360360208110156101c957600080fd5b503561060a565b6040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156102135781810151838201526020016101fb565b50505050905090810190601f1680156102405780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b34801561025b57600080fd5b5061008a6004803603606081101561027257600080fd5b8135919081019060408101602082013564010000000081111561029457600080fd5b8201836020820111156102a657600080fd5b803590602001918460018302840111640100000000831117156102c857600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955050913592506106c0915050565b34801561031757600080fd5b506101206004803603604081101561032e57600080fd5b506001600160a01b038135169060200135610777565b3360009081526001602052604090205460ff161561036157600080fd5b336000908152600160208190526040909120805460ff19169091179055565b60016020526000908152604090205460ff1681565b3360009081526001602081905260409091205460ff161515146103b757600080fd5b600082815260036020526040902060010154810234146103d657600080fd5b600080546040516001600160a01b0390911691303180156108fc02929091818181858888f19350505050158015610411573d6000803e3d6000fd5b506040805160008481526003602090815290839020805460026001821615610100026000190190911604601f810183900490920283016080908101909452606083018281529293849392908401828280156104ad5780601f10610482576101008083540402835291602001916104ad565b820191906000526020600020905b81548152906001019060200180831161049057829003601f168201915b505050918352505033600090815260026020908152604080832087845280835281842060018101805489019081905584870152429290950191909152918690529081528251805161050192849201906107e7565b506020828101516001830155604092830151600290920191909155815133815290810184905280820183905290517fa59ac6dd8b1d00e4c8ba9abba262aaac3d4d05e77324205b07a39a002e479b5f9181900360600190a15050565b60036020908152600091825260409182902080548351601f600260001961010060018616150201909316929092049182018490048402810184019094528084529092918391908301828280156105f45780601f106105c9576101008083540402835291602001916105f4565b820191906000526020600020905b8154815290600101906020018083116105d757829003601f168201915b5050505050908060010154908060020154905083565b3360009081526002602081815260408084208585528252808420600180820154825484519281161561010002600019011695909504601f8101859004850282018501909352828152606095949193919290918491908301828280156106b05780601f10610685576101008083540402835291602001916106b0565b820191906000526020600020905b81548152906001019060200180831161069357829003601f168201915b5050505050915091509150915091565b6000546001600160a01b03163314610713576040805162461bcd60e51b81526020600482015260116024820152702cb7ba9030b932903737ba1037bbb732b960791b604482015290519081900360640190fd5b60408051606081018252838152670de0b6b3a76400008302602080830191909152428284015260008681526003825292909220815180519293919261075b92849201906107e7565b5060208201516001820155604090910151600290910155505050565b6002602081815260009384526040808520825292845292829020805483516001821615610100026000190190911692909204601f810185900485028301850190935282825292909183918301828280156105f45780601f106105c9576101008083540402835291602001916105f4565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061082857805160ff1916838001178555610855565b82800160010185558215610855579182015b8281111561085557825182559160200191906001019061083a565b50610861929150610865565b5090565b61087f91905b80821115610861576000815560010161086b565b9056fea265627a7a7231582053547d1426a751706e791e25b9edb6eda691d8d9626ddc108a888f0c1e6cc39864736f6c634300050d0032";

    public static final String FUNC_BUYLIST = "buyList";

    public static final String FUNC_BUYPRODUCT = "buyProduct";

    public static final String FUNC_GETHISTORY = "getHistory";

    public static final String FUNC_ISREGISTER = "isRegister";

    public static final String FUNC_PRODUCT = "product";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_SETPRODUCT = "setProduct";

    public static final Event BUY_EVENT = new Event("buy", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Shop(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Shop(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Shop(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Shop(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<BuyEventResponse> getBuyEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BUY_EVENT, transactionReceipt);
        ArrayList<BuyEventResponse> responses = new ArrayList<BuyEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BuyEventResponse typedResponse = new BuyEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BuyEventResponse> buyEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BuyEventResponse>() {
            @Override
            public BuyEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BUY_EVENT, log);
                BuyEventResponse typedResponse = new BuyEventResponse();
                typedResponse.log = log;
                typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BuyEventResponse> buyEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BUY_EVENT));
        return buyEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>> buyList(String param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BUYLIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> buyProduct(BigInteger _ID, BigInteger _amount, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BUYPRODUCT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_ID), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<Tuple2<String, BigInteger>> getHistory(BigInteger _ID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETHISTORY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_ID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<String, BigInteger>>(function,
                new Callable<Tuple2<String, BigInteger>>() {
                    @Override
                    public Tuple2<String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Boolean> isRegister(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISREGISTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>> product(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PRODUCT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> register() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setProduct(BigInteger _ID, String _name, BigInteger _price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETPRODUCT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_ID), 
                new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.generated.Uint256(_price)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Shop load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Shop(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Shop load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Shop(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Shop load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Shop(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Shop load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Shop(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Shop> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Shop.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Shop> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Shop.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Shop> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Shop.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Shop> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Shop.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class BuyEventResponse extends BaseEventResponse {
        public String buyer;

        public BigInteger id;

        public BigInteger amount;
    }
}
