package smartBot.data.repository.types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGInterval;
import smartBot.utils.type.Duration;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Interval implements UserType {

    public Class<?> returnedClass() {
        return Duration.class;
    }


    public int[] sqlTypes() {
        return new int[] {Types.OTHER};
    }


    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
        throws HibernateException, SQLException {

        try {
            final PGInterval pgi = (PGInterval)resultSet.getObject(names[0]);

            final int years = pgi.getYears();
            final int months = pgi.getMonths();
            final int days = pgi.getDays();
            final int hours = pgi.getHours();
            final int mins = pgi.getMinutes();
            final double secs = pgi.getSeconds();

            return new Duration(years, months, 0, days, hours);

        }
        catch (Exception e) {
            return null;
        }
    }


    public void nullSafeSet(PreparedStatement statement, Object value, int index)
        throws HibernateException, SQLException {

        if (value == null) {
            statement.setNull(index, Types.OTHER);
        }
        else {
            final Duration duration = (Duration)value;

            final int years = duration.getYears();
            final int months = duration.getMonths();
            final int days = duration.getDays();
            final int hours = 0;
            final int mins = 0;
            final int secs = 0;

            final PGInterval pgi = new PGInterval(years, months, days, hours, mins, secs);
            statement.setObject(index, pgi);
        }
    }


    public boolean equals(Object x, Object y)
        throws HibernateException {

        return x == y;
    }


    public int hashCode(Object x)
        throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        return nullSafeGet(resultSet, strings, o);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sessionImplementor) throws HibernateException, SQLException {
        nullSafeSet(preparedStatement, o, i);
    }


    public Object deepCopy(Object value)
        throws HibernateException {
        return value;
    }


    public boolean isMutable() {
        return false;
    }


    public Serializable disassemble(Object value)
        throws HibernateException {
        throw new HibernateException("not implemented");
    }


    public Object assemble(Serializable cached, Object owner)
        throws HibernateException {
        throw new HibernateException("not implemented");
    }


    public Object replace(Object original, Object target, Object owner)
        throws HibernateException {
        return target;
    }
}
