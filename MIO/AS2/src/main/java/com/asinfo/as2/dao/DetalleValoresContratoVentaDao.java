/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ContratoVenta;
/*   4:    */ import com.asinfo.as2.entities.DetalleValoresContratoVenta;
/*   5:    */ import com.asinfo.as2.entities.Documento;
/*   6:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ import javax.persistence.EntityManager;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class DetalleValoresContratoVentaDao
/*  19:    */   extends AbstractDaoAS2<DetalleValoresContratoVenta>
/*  20:    */ {
/*  21:    */   public DetalleValoresContratoVentaDao()
/*  22:    */   {
/*  23: 32 */     super(DetalleValoresContratoVenta.class);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List<DetalleValoresContratoVenta> listaDetalleValoresContratoVenta(Date fecha, Organizacion organizacion)
/*  27:    */   {
/*  28: 42 */     StringBuilder sql = new StringBuilder();
/*  29:    */     
/*  30: 44 */     sql.append(" SELECT dvcv ");
/*  31: 45 */     sql.append(" FROM DetalleValoresContratoVenta dvcv ");
/*  32: 46 */     sql.append(" INNER JOIN FETCH dvcv.contratoVenta cv ");
/*  33: 47 */     sql.append(" WHERE dvcv.fecha <= :fecha ");
/*  34: 48 */     sql.append(" AND (dvcv.indicadorContabilizado IS NULL OR dvcv.indicadorContabilizado = false) ");
/*  35: 49 */     sql.append(" AND dvcv.indicadorDevengado = true ");
/*  36: 50 */     sql.append(" AND dvcv.valorDevengar != 0 ");
/*  37: 51 */     sql.append(" AND cv.estado != :estado ");
/*  38: 52 */     sql.append(" AND dvcv.idOrganizacion = :idOrganizacion ");
/*  39:    */     
/*  40: 54 */     Query query = this.em.createQuery(sql.toString());
/*  41: 55 */     query.setParameter("fecha", fecha);
/*  42: 56 */     query.setParameter("estado", Estado.ANULADO);
/*  43: 57 */     query.setParameter("idOrganizacion", Integer.valueOf(organizacion.getId()));
/*  44:    */     
/*  45: 59 */     List<DetalleValoresContratoVenta> lista = query.getResultList();
/*  46:    */     
/*  47: 61 */     return lista;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public List<DetalleValoresContratoVenta> listaDetalleValoresContratoVenta(ContratoVenta cv, DocumentoBase documentoBase, FacturaCliente facturaPadre)
/*  51:    */   {
/*  52: 72 */     StringBuilder sql = new StringBuilder();
/*  53:    */     
/*  54: 74 */     sql.append(" SELECT dvcv ");
/*  55: 75 */     sql.append(" FROM DetalleValoresContratoVenta dvcv ");
/*  56: 76 */     sql.append(" INNER JOIN FETCH dvcv.contratoVenta cv ");
/*  57: 77 */     sql.append(" LEFT JOIN FETCH dvcv.facturaCliente fc ");
/*  58:    */     
/*  59: 79 */     sql.append(" WHERE dvcv.contratoVenta = :contratoVenta ");
/*  60: 80 */     if (documentoBase.equals(DocumentoBase.FACTURA_CLIENTE))
/*  61:    */     {
/*  62: 81 */       sql.append(" AND dvcv.indicadorFacturado = false ");
/*  63: 82 */       sql.append(" AND dvcv.valor > 0 ");
/*  64:    */     }
/*  65: 84 */     if ((documentoBase.equals(DocumentoBase.NOTA_CREDITO_CLIENTE)) || (documentoBase.equals(DocumentoBase.DEVOLUCION_CLIENTE)))
/*  66:    */     {
/*  67: 85 */       sql.append(" AND dvcv.indicadorFacturado = true ");
/*  68: 86 */       sql.append(" AND dvcv.valorDevengar > 0 ");
/*  69: 87 */       sql.append(" AND fc.idFacturaCliente =:idFacturaPadre ");
/*  70:    */     }
/*  71: 89 */     sql.append(" AND cv.estado != :estado ");
/*  72:    */     
/*  73: 91 */     sql.append(" ORDER BY dvcv.fecha ");
/*  74:    */     
/*  75: 93 */     Query query = this.em.createQuery(sql.toString());
/*  76: 94 */     query.setParameter("estado", Estado.ANULADO);
/*  77: 95 */     query.setParameter("contratoVenta", cv);
/*  78: 96 */     if (facturaPadre != null) {
/*  79: 97 */       query.setParameter("idFacturaPadre", Integer.valueOf(facturaPadre.getId()));
/*  80:    */     }
/*  81:100 */     List<DetalleValoresContratoVenta> lista = query.getResultList();
/*  82:    */     
/*  83:102 */     return lista;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public List<DetalleValoresContratoVenta> obtenerListaDetalleValoresContratoVentaPorFactura(FacturaCliente facturaCliente)
/*  87:    */   {
/*  88:107 */     StringBuilder sql = new StringBuilder();
/*  89:    */     
/*  90:109 */     sql.append(" SELECT dvcv ");
/*  91:110 */     sql.append(" FROM DetalleValoresContratoVenta dvcv ");
/*  92:111 */     if (facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.FACTURA_CLIENTE))
/*  93:    */     {
/*  94:112 */       sql.append(" INNER JOIN dvcv.facturaCliente fc ");
/*  95:113 */       sql.append(" WHERE fc.idFacturaCliente = :idFactura ");
/*  96:    */     }
/*  97:115 */     if ((facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.NOTA_CREDITO_CLIENTE)) || (facturaCliente.getDocumento().getDocumentoBase().equals(DocumentoBase.DEVOLUCION_CLIENTE)))
/*  98:    */     {
/*  99:116 */       sql.append(" INNER JOIN dvcv.notaCreditoCliente ncc ");
/* 100:117 */       sql.append(" WHERE ncc.idFacturaCliente = :idFactura ");
/* 101:    */     }
/* 102:119 */     sql.append(" ORDER BY dvcv.fecha ");
/* 103:    */     
/* 104:121 */     Query query = this.em.createQuery(sql.toString());
/* 105:122 */     query.setParameter("idFactura", Integer.valueOf(facturaCliente.getId()));
/* 106:    */     
/* 107:124 */     List<DetalleValoresContratoVenta> lista = query.getResultList();
/* 108:    */     
/* 109:126 */     return lista;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void liberarDetalleValoresContratoVentaInterfazContable(InterfazContableProceso interfazContableProceso)
/* 113:    */   {
/* 114:131 */     String sql = "UPDATE DetalleValoresContratoVenta dvcv  SET dvcv.indicadorContabilizado = false, dvcv.interfazContableProceso = NULL\tWHERE dvcv.interfazContableProceso = :interfazContableProceso";
/* 115:    */     
/* 116:    */ 
/* 117:134 */     Query query = this.em.createQuery(sql);
/* 118:135 */     query.setParameter("interfazContableProceso", interfazContableProceso);
/* 119:136 */     query.executeUpdate();
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleValoresContratoVentaDao
 * JD-Core Version:    0.7.0.1
 */