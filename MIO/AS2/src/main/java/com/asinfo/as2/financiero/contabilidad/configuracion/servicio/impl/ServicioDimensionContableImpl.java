/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DimensionContableDao;
/*   4:    */ import com.asinfo.as2.entities.CuentaContableDimensionContable;
/*   5:    */ import com.asinfo.as2.entities.DimensionContable;
/*   6:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   9:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  10:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  11:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.ejb.Stateless;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class ServicioDimensionContableImpl
/*  23:    */   implements ServicioDimensionContable
/*  24:    */ {
/*  25:    */   @EJB
/*  26:    */   private DimensionContableDao dimensionContableDao;
/*  27:    */   @EJB
/*  28:    */   private ServicioCuentaContable servicioCuentaContable;
/*  29:    */   @EJB
/*  30:    */   private ServicioGenerico<CuentaContableDimensionContable> servicioCuentaContableDimensionContable;
/*  31:    */   
/*  32:    */   public void guardar(DimensionContable dimensionContable)
/*  33:    */     throws ExcepcionAS2Financiero
/*  34:    */   {
/*  35:    */     try
/*  36:    */     {
/*  37: 56 */       this.dimensionContableDao.guardar(dimensionContable);
/*  38: 65 */       for (CuentaContableDimensionContable cuentaContableDimensionContable : dimensionContable.getListaCuentaContableDimensionContable()) {
/*  39: 70 */         this.servicioCuentaContableDimensionContable.guardar(cuentaContableDimensionContable);
/*  40:    */       }
/*  41:    */     }
/*  42:    */     catch (AS2Exception e)
/*  43:    */     {
/*  44: 73 */       e.printStackTrace();
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void eliminar(DimensionContable dimensionContable)
/*  49:    */   {
/*  50: 85 */     this.dimensionContableDao.eliminar(dimensionContable);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public DimensionContable buscarPorId(Integer id)
/*  54:    */   {
/*  55: 96 */     return (DimensionContable)this.dimensionContableDao.buscarPorId(id);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public DimensionContable verificaMovimiento(Integer id)
/*  59:    */     throws ExcepcionAS2Financiero
/*  60:    */   {
/*  61:107 */     DimensionContable dimensionContable = buscarPorId(id);
/*  62:108 */     if (!dimensionContable.isIndicadorMovimiento())
/*  63:    */     {
/*  64:109 */       dimensionContable = new DimensionContable();
/*  65:110 */       dimensionContable.setIdDimensionContable(-1);
/*  66:111 */       throw new ExcepcionAS2Financiero("msg_info_cuenta_contable_0001");
/*  67:    */     }
/*  68:113 */     return dimensionContable;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<DimensionContable> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  72:    */   {
/*  73:124 */     return this.dimensionContableDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public List<DimensionContable> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  77:    */   {
/*  78:136 */     return this.dimensionContableDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public DimensionContable cargarDetalle(int idDimensionContable)
/*  82:    */   {
/*  83:146 */     return this.dimensionContableDao.cargarDetalle(idDimensionContable);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int contarPorCriterio(Map<String, String> filters)
/*  87:    */   {
/*  88:156 */     return this.dimensionContableDao.contarPorCriterio(filters);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public List<DimensionContable> obtenerListaDimensionPadre(DimensionContable dimensionContable)
/*  92:    */   {
/*  93:168 */     String mascaraHijo = dimensionContable.getMascara();
/*  94:169 */     String mascaraPadre = "";
/*  95:170 */     String[] longitudes = mascaraHijo.split("\\.");
/*  96:171 */     if (longitudes.length > 1)
/*  97:    */     {
/*  98:172 */       for (int i = 0; i < longitudes.length - 1; i++)
/*  99:    */       {
/* 100:173 */         System.out.println("");
/* 101:174 */         mascaraPadre = mascaraPadre + longitudes[i] + ".";
/* 102:    */       }
/* 103:176 */       Map<String, String> filtros = new HashMap();
/* 104:177 */       filtros.put("numero", dimensionContable.getNumero());
/* 105:178 */       filtros.put("mascara", "=" + mascaraPadre);
/* 106:179 */       return obtenerListaCombo("codigo", true, filtros);
/* 107:    */     }
/* 108:181 */     return new ArrayList();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<DimensionContable> obtenerListaDimensionPadreRecursivo(DimensionContable dimensionContable)
/* 112:    */   {
/* 113:196 */     List<DimensionContable> lista = new ArrayList();
/* 114:197 */     String mascaraHijo = dimensionContable.getMascara();
/* 115:198 */     String mascaraPadre = "";
/* 116:199 */     String[] longitudes = mascaraHijo.split("\\.");
/* 117:200 */     if (longitudes.length > 1) {
/* 118:201 */       for (int i = 0; i < longitudes.length - 1; i++)
/* 119:    */       {
/* 120:202 */         System.out.println("");
/* 121:203 */         mascaraPadre = mascaraPadre + longitudes[i] + ".";
/* 122:204 */         Map<String, String> filtros = new HashMap();
/* 123:205 */         filtros.put("numero", dimensionContable.getNumero());
/* 124:206 */         filtros.put("mascara", "=" + mascaraPadre);
/* 125:207 */         lista.addAll(obtenerListaCombo("codigo", true, filtros));
/* 126:    */       }
/* 127:    */     } else {
/* 128:210 */       return new ArrayList();
/* 129:    */     }
/* 130:213 */     FuncionesUtiles.ordenaLista(lista, "codigo");
/* 131:    */     
/* 132:215 */     return lista;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public DimensionContable buscarPorCodigo(String numeroDimension, String codigo)
/* 136:    */     throws ExcepcionAS2
/* 137:    */   {
/* 138:220 */     return this.dimensionContableDao.buscarPorCodigo(numeroDimension, codigo);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List<Object[]> getReporteDimensionContable(int idDimensionContable, int idOrganizacion)
/* 142:    */   {
/* 143:225 */     return this.dimensionContableDao.getReporteDimensionContable(idDimensionContable, idOrganizacion);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public List<DimensionContable> obtenerDimensionContablePorUsuario(int idOrganizacion, int idUsuario, int longitudCodigo, boolean indicadorPresupuesto)
/* 147:    */   {
/* 148:231 */     return this.dimensionContableDao.obtenerDimensionContablePorUsuario(idOrganizacion, idUsuario, longitudCodigo, indicadorPresupuesto);
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioDimensionContableImpl
 * JD-Core Version:    0.7.0.1
 */