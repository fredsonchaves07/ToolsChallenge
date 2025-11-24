package tools.challenge.api.database.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public final class EntityNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    public Identifier toPhysicalTableName(final Identifier name, final JdbcEnvironment context) {
        String tableName = "\"" + name.getText().toUpperCase() + "\"";
        return Identifier.toIdentifier(tableName, true);
    }

    public Identifier toPhysicalColumnName(final Identifier name, final JdbcEnvironment context) {
        String columnName = "\"" + name.getText().toUpperCase() + "\"";
        return Identifier.toIdentifier(columnName, true);
    }
}
