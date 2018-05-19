package smartBot.data.repository.types;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

public class Int4RangeType implements UserType {

    /**
     * PostgreSQL {@code int4range} field separator token.
     */
    private static final String INT4RANGE_SEPARATOR_TOKEN = ",";

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.OTHER};
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class returnedClass() {
        return Map.class;
    }

    @Override
    public boolean equals(final Object x, final Object y) throws HibernateException {
        return x.equals(y);
    }

    @Override
    public int hashCode(final Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names,
                              final SharedSessionContractImplementor session, final Object owner)
        throws HibernateException, SQLException {
        return convertToEntityAttribute(rs.getString(names[0]));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void nullSafeSet(final PreparedStatement st, final Object value, final int index,
                            final SharedSessionContractImplementor session) throws HibernateException, SQLException {
        st.setObject(index, convertToDatabaseColumn((Range<Integer>) value), Types.OTHER);

    }

    @SuppressWarnings("unchecked")
    @Override
    public Object deepCopy(final Object value) throws HibernateException {
        if (value == null) return new Range<Integer>();
        return (Range<Integer>) value;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(final Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(final Serializable cached, final Object owner)
        throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner)
        throws HibernateException {
        return original;
    }


    private String convertToDatabaseColumn(final Range<Integer> attribute) {

        if (attribute == null) return "";
        return attribute.toString();
    }

    private Range<Integer> convertToEntityAttribute(final String dbData) {
        if (StringUtils.isEmpty(dbData)) return new Range<Integer>();

        Range<Integer> iRange = new Range<>(dbData);
        String dbDataTmp = dbData.substring(1, dbData.length()-1);

        Object[] iValues = dbDataTmp.split(INT4RANGE_SEPARATOR_TOKEN);
        if (iValues != null && iValues.length > 1) {
            iRange.setMinValue((Integer.parseInt((String)iValues[0])));
            iRange.setMaxValue((Integer.parseInt((String)iValues[1])));
        }
        return iRange;
    }
}
