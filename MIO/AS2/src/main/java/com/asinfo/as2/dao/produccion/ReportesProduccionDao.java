/*   1:    */ package com.asinfo.as2.dao.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.db.AS2DBBase;
/*   4:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   5:    */ import com.asinfo.as2.entities.Producto;
/*   6:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterial;
/*   8:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*  11:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import javax.ejb.Stateless;
/*  17:    */ import javax.persistence.EntityManager;
/*  18:    */ import javax.persistence.Query;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class ReportesProduccionDao
/*  22:    */   extends AS2DBBase
/*  23:    */ {
/*  24:    */   public List<Object[]> reporteOrdenFabricacion(Date fecha, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto)
/*  25:    */   {
/*  26: 41 */     StringBuilder sql = new StringBuilder();
/*  27: 42 */     sql.append(" SELECT ofa.numero, p.codigo, p.nombre, ofa.cantidadBatch, p.cantidadProduccion, ofa.cantidad, uni.codigo, ofa.cantidadFabricada, ofa.descripcion, cp.nombre, scp.nombre ");
/*  28:    */     
/*  29: 44 */     sql.append(" FROM OrdenFabricacion ofa ");
/*  30: 45 */     sql.append(" INNER JOIN ofa.producto p ");
/*  31: 46 */     sql.append(" INNER JOIN p.unidad uni ");
/*  32: 47 */     sql.append(" INNER JOIN p.subcategoriaProducto scp ");
/*  33: 48 */     sql.append(" INNER JOIN scp.categoriaProducto cp ");
/*  34: 49 */     sql.append(" WHERE ofa.fecha =:fecha ");
/*  35: 50 */     sql.append(" AND ofa.estado != :estadoAnulado ");
/*  36: 51 */     if (categoriaProducto != null) {
/*  37: 52 */       sql.append(" AND cp = :categoriaProducto ");
/*  38:    */     }
/*  39: 54 */     if (subcategoriaProducto != null) {
/*  40: 55 */       sql.append(" AND scp = :subcategoriaProducto ");
/*  41:    */     }
/*  42: 57 */     sql.append(" ORDER BY cp.nombre ");
/*  43:    */     
/*  44: 59 */     Query query = this.em.createQuery(sql.toString());
/*  45: 60 */     query.setParameter("fecha", fecha);
/*  46: 61 */     query.setParameter("estadoAnulado", EstadoProduccionEnum.ANULADO);
/*  47: 62 */     if (categoriaProducto != null) {
/*  48: 63 */       query.setParameter("categoriaProducto", categoriaProducto);
/*  49:    */     }
/*  50: 65 */     if (subcategoriaProducto != null) {
/*  51: 66 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/*  52:    */     }
/*  53: 69 */     return query.getResultList();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public List<DetalleOrdenFabricacionMaterial> listaMaterialesRendimiento(Date fecha, Date fechaHasta, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, int idOrganizacion)
/*  57:    */   {
/*  58: 76 */     StringBuilder sql = new StringBuilder();
/*  59: 77 */     sql.append(" SELECT dofm  ");
/*  60: 78 */     sql.append(" FROM DetalleOrdenFabricacionMaterial dofm ");
/*  61: 79 */     sql.append(" INNER JOIN dofm.ordenFabricacion ofab ");
/*  62: 80 */     sql.append(" INNER JOIN ofab.ordenSalidaMaterialPrincipal osm ");
/*  63: 81 */     sql.append(" INNER JOIN dofm.material m ");
/*  64: 82 */     sql.append(" INNER JOIN ofab.producto pro ");
/*  65: 83 */     sql.append(" INNER JOIN m.subcategoriaProducto scp ");
/*  66: 84 */     sql.append(" INNER JOIN scp.categoriaProducto cp ");
/*  67: 85 */     sql.append(" WHERE dofm.idOrganizacion = :idOrganizacion ");
/*  68: 86 */     if ((categoriaProducto != null) && (producto == null)) {
/*  69: 87 */       sql.append(" AND cp = :categoriaProducto ");
/*  70:    */     }
/*  71: 89 */     if ((subcategoriaProducto != null) && (producto == null)) {
/*  72: 90 */       sql.append(" AND scp = :subcategoriaProducto ");
/*  73:    */     }
/*  74: 92 */     if (producto != null) {
/*  75: 93 */       sql.append(" AND m = :producto ");
/*  76:    */     }
/*  77: 95 */     sql.append(" AND dofm.indicadorHoja = false ");
/*  78: 96 */     sql.append(" AND ofab.estado = :estado ");
/*  79: 97 */     sql.append(" AND osm.estado = :estadoCerrada ");
/*  80:    */     
/*  81: 99 */     sql.append(" AND ofab.fechaCierre BETWEEN :fecha AND :fechaHasta ");
/*  82:    */     
/*  83:101 */     Query query = this.em.createQuery(sql.toString());
/*  84:102 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  85:    */     
/*  86:104 */     query.setParameter("fecha", FuncionesUtiles.setAtributoFecha(fecha));
/*  87:105 */     System.out.println(FuncionesUtiles.setAtributoFecha(fecha));
/*  88:106 */     query.setParameter("fechaHasta", FuncionesUtiles.setFechaMilisegundos(fechaHasta, 23, 59, 59, 999));
/*  89:107 */     System.out.println(FuncionesUtiles.setFechaMilisegundos(fechaHasta, 23, 59, 59, 999));
/*  90:108 */     query.setParameter("estado", EstadoProduccionEnum.FINALIZADA);
/*  91:109 */     query.setParameter("estadoCerrada", Estado.CERRADO);
/*  92:110 */     if (categoriaProducto != null) {
/*  93:111 */       query.setParameter("categoriaProducto", categoriaProducto);
/*  94:    */     }
/*  95:113 */     if (subcategoriaProducto != null) {
/*  96:114 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/*  97:    */     }
/*  98:116 */     if (producto != null) {
/*  99:117 */       query.setParameter("producto", producto);
/* 100:    */     }
/* 101:120 */     return query.getResultList();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public List<DetalleOrdenFabricacionMaterial> detallesOrdenFabricacionMaterialesHijos(DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterial)
/* 105:    */   {
/* 106:127 */     StringBuilder sql = new StringBuilder();
/* 107:128 */     sql.append(" SELECT dofm  ");
/* 108:129 */     sql.append(" FROM DetalleOrdenFabricacionMaterial dofm ");
/* 109:130 */     sql.append(" WHERE dofm.detalleOrdenFabricacionMaterialPadre = :detalleOrdenFabricacionMaterial ");
/* 110:    */     
/* 111:132 */     Query query = this.em.createQuery(sql.toString());
/* 112:133 */     query.setParameter("detalleOrdenFabricacionMaterial", detalleOrdenFabricacionMaterial);
/* 113:134 */     return query.getResultList();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public BigDecimal cantidadUtilizadaReal(Producto producto, OrdenFabricacion ordenFabricacion)
/* 117:    */   {
/* 118:139 */     StringBuilder sql = new StringBuilder();
/* 119:140 */     sql.append(" SELECT COALESCE(SUM(dosmof.cantidadUtilizada), 0) ");
/* 120:141 */     sql.append(" FROM DetalleOrdenSalidaMaterialOrdenFabricacion dosmof ");
/* 121:142 */     sql.append(" INNER JOIN dosmof.ordenFabricacion ofab ");
/* 122:143 */     sql.append(" INNER JOIN dosmof.detalleOrdenSalidaMaterial dosm ");
/* 123:144 */     sql.append(" INNER JOIN dosm.producto p ");
/* 124:145 */     sql.append(" WHERE p = :producto ");
/* 125:146 */     sql.append(" AND ofab = :ordenFabricacion ");
/* 126:147 */     sql.append(" AND ofab.estado = :estado ");
/* 127:    */     
/* 128:149 */     Query query = this.em.createQuery(sql.toString());
/* 129:150 */     query.setParameter("producto", producto);
/* 130:151 */     query.setParameter("ordenFabricacion", ordenFabricacion);
/* 131:152 */     query.setParameter("estado", EstadoProduccionEnum.FINALIZADA);
/* 132:153 */     BigDecimal resultado = (BigDecimal)query.getSingleResult();
/* 133:    */     
/* 134:155 */     return resultado;
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.produccion.ReportesProduccionDao
 * JD-Core Version:    0.7.0.1
 */