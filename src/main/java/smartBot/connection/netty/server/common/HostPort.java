package smartBot.connection.netty.server.common;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.net.InetSocketAddress;

/**
 * A HostPort is a simple bean encapsulating which host and port has made a
 * connection. Useful on the server side to keep track of who is connected,
 * where subscriptions go etc.
 */
public class HostPort implements Serializable {

    private static final long serialVersionUID = 7989783689512750362L;

    private final String host;
    private final int port;

    /**
     * Instantiates a new host port.
     *
     * @param host
     *          the host
     * @param port
     *          the port
     */
    public HostPort(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Instantiates a new host port.
     *
     * @param address
     *          the address
     */
    public HostPort(InetSocketAddress address) {
        this.host = address.getAddress().getHostAddress();
        this.port = address.getPort();
    }

    /**
     * Gets the host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets the port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getHost() + ":" + getPort();
    }

}
