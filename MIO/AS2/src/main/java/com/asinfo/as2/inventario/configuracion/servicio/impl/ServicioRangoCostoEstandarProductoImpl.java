/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CostoEstandarProductoDao;
/*   4:    */ import com.asinfo.as2.dao.RangoCostoEstandarProductoDao;
/*   5:    */ import com.asinfo.as2.entities.CostoEstandarProducto;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.RangoCostoEstandarProducto;
/*   8:    */ import com.asinfo.as2.enumeraciones.FormatoCelda;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioRangoCostoEstandarProducto;
/*  13:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  14:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  15:    */ import java.io.InputStream;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.math.RoundingMode;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.Resource;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.ejb.SessionContext;
/*  24:    */ import javax.ejb.Stateless;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  27:    */ 
/*  28:    */ @Stateless
/*  29:    */ public class ServicioRangoCostoEstandarProductoImpl
/*  30:    */   extends AbstractServicioAS2
/*  31:    */   implements ServicioRangoCostoEstandarProducto
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @EJB
/*  35:    */   private RangoCostoEstandarProductoDao rangoCostoEstandarProductoDao;
/*  36:    */   @EJB
/*  37:    */   private CostoEstandarProductoDao costoEstandarProductoDao;
/*  38:    */   @EJB
/*  39:    */   private ServicioProducto servicioProducto;
/*  40:    */   @Resource
/*  41:    */   protected SessionContext context;
/*  42:    */   
/*  43:    */   public RangoCostoEstandarProducto guardar(RangoCostoEstandarProducto rangoCostoEstandarProducto)
/*  44:    */   {
/*  45: 73 */     for (CostoEstandarProducto costoEstandarProducto : rangoCostoEstandarProducto.getListaCostoEstandarProducto()) {
/*  46: 74 */       this.costoEstandarProductoDao.guardar(costoEstandarProducto);
/*  47:    */     }
/*  48: 76 */     this.rangoCostoEstandarProductoDao.guardar(rangoCostoEstandarProducto);
/*  49: 77 */     return rangoCostoEstandarProducto;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void eliminar(RangoCostoEstandarProducto rangoCostoEstandarProducto)
/*  53:    */   {
/*  54: 89 */     this.rangoCostoEstandarProductoDao.eliminar(rangoCostoEstandarProducto);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<RangoCostoEstandarProducto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  58:    */   {
/*  59:102 */     return this.rangoCostoEstandarProductoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<RangoCostoEstandarProducto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  63:    */   {
/*  64:115 */     return this.rangoCostoEstandarProductoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int contarPorCriterio(Map<String, String> filters)
/*  68:    */   {
/*  69:126 */     return this.rangoCostoEstandarProductoDao.contarPorCriterio(filters);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public RangoCostoEstandarProducto buscarPorId(Integer id)
/*  73:    */   {
/*  74:137 */     return (RangoCostoEstandarProducto)this.rangoCostoEstandarProductoDao.buscarPorId(id);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public RangoCostoEstandarProducto cargarDetalle(int idRangoCostoEstandarProducto)
/*  78:    */   {
/*  79:148 */     return this.rangoCostoEstandarProductoDao.cargarDetalle(idRangoCostoEstandarProducto);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void cargaCostoEstandarProducto(RangoCostoEstandarProducto rangoCostoEstandarProducto, InputStream imInputStream, int filaInicial)
/*  83:    */     throws ExcepcionAS2
/*  84:    */   {
/*  85:165 */     HashMap<Integer, CostoEstandarProducto> hmCostoEstandarProducto = new HashMap();
/*  86:166 */     for (CostoEstandarProducto costoEstandarProducto : rangoCostoEstandarProducto.getListaCostoEstandarProducto()) {
/*  87:167 */       hmCostoEstandarProducto.put(Integer.valueOf(costoEstandarProducto.getProducto().getId()), costoEstandarProducto);
/*  88:    */     }
/*  89:171 */     Object filters = new HashMap();
/*  90:172 */     ((HashMap)filters).put("tipoProducto", TipoProducto.ARTICULO.toString());
/*  91:173 */     ((HashMap)filters).put("activo", "true");
/*  92:174 */     List<Producto> listaProductos = this.servicioProducto.obtenerListaCombo("codigo", true, (Map)filters);
/*  93:175 */     HashMap<String, Producto> hmProducto = new HashMap();
/*  94:176 */     for (Producto producto : listaProductos) {
/*  95:177 */       hmProducto.put(producto.getCodigo(), producto);
/*  96:    */     }
/*  97:180 */     int filaActual = filaInicial;
/*  98:181 */     int columnaActual = 0;
/*  99:182 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 100:    */     try
/* 101:    */     {
/* 102:185 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 103:187 */       for (HSSFCell[] fila : datos)
/* 104:    */       {
/* 105:189 */         filaErronea = fila;
/* 106:190 */         filaActual++;
/* 107:    */         
/* 108:    */ 
/* 109:193 */         String codigoProducto = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 0, true, Integer.valueOf(1), Integer.valueOf(20));
/* 110:194 */         Producto producto = (Producto)hmProducto.get(codigoProducto);
/* 111:195 */         if (producto == null) {
/* 112:197 */           throw new ExcepcionAS2("msg_error_no_existe_dato_sistema", "Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + fila[columnaActual].toString());
/* 113:    */         }
/* 114:200 */         String nombreProducto = (String)FuncionesUtiles.validarCelda(fila, FormatoCelda.TEXTO, filaActual, columnaActual = 1, true, Integer.valueOf(1), Integer.valueOf(20));
/* 115:    */         
/* 116:202 */         BigDecimal costo = (BigDecimal)FuncionesUtiles.validarCelda(fila, FormatoCelda.COSTO, filaActual, columnaActual = 2, true, Integer.valueOf(1), Integer.valueOf(20));
/* 117:    */         
/* 118:204 */         CostoEstandarProducto costoEstandarProducto = (CostoEstandarProducto)hmCostoEstandarProducto.get(Integer.valueOf(producto.getId()));
/* 119:205 */         if ((costoEstandarProducto == null) && (costo.compareTo(BigDecimal.ZERO) > 0))
/* 120:    */         {
/* 121:206 */           costoEstandarProducto = new CostoEstandarProducto();
/* 122:207 */           costoEstandarProducto.setIdOrganizacion(rangoCostoEstandarProducto.getIdOrganizacion());
/* 123:208 */           costoEstandarProducto.setIdSucursal(rangoCostoEstandarProducto.getIdSucursal());
/* 124:209 */           costoEstandarProducto.setProducto(producto);
/* 125:210 */           costoEstandarProducto.setRangoCostoEstandarProducto(rangoCostoEstandarProducto);
/* 126:211 */           rangoCostoEstandarProducto.getListaCostoEstandarProducto().add(costoEstandarProducto);
/* 127:    */         }
/* 128:214 */         if (costoEstandarProducto != null)
/* 129:    */         {
/* 130:215 */           costoEstandarProducto.setCosto(costo.setScale(4, RoundingMode.HALF_UP));
/* 131:216 */           costoEstandarProducto.setEliminado(costo.compareTo(BigDecimal.ZERO) == 0);
/* 132:    */         }
/* 133:    */       }
/* 134:    */     }
/* 135:    */     catch (IllegalStateException e)
/* 136:    */     {
/* 137:221 */       LOG.info("Error al migrar productos", e);
/* 138:222 */       this.context.setRollbackOnly();
/* 139:223 */       e.printStackTrace();
/* 140:    */       
/* 141:225 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString());
/* 142:    */     }
/* 143:    */     catch (ExcepcionAS2 e)
/* 144:    */     {
/* 145:227 */       LOG.info("Error al migrar productos", e);
/* 146:228 */       this.context.setRollbackOnly();
/* 147:229 */       e.printStackTrace();
/* 148:230 */       throw e;
/* 149:    */     }
/* 150:    */     catch (Exception e)
/* 151:    */     {
/* 152:232 */       LOG.error("Error al migrar productos", e);
/* 153:233 */       this.context.setRollbackOnly();
/* 154:234 */       e.printStackTrace();
/* 155:    */       
/* 156:236 */       throw new ExcepcionAS2("msg_error_cargar_datos", " Fila: " + filaActual + " Columna: " + columnaActual + " Dato: " + filaErronea[columnaActual].toString(), e);
/* 157:    */     }
/* 158:    */   }
/* 159:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioRangoCostoEstandarProductoImpl
 * JD-Core Version:    0.7.0.1
 */