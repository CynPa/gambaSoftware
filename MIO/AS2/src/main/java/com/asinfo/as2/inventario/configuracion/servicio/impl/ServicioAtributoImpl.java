/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AtributoDao;
/*   4:    */ import com.asinfo.as2.dao.ValorAtributoDao;
/*   5:    */ import com.asinfo.as2.entities.Atributo;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.ValorAtributo;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProductoAtributo;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import java.io.InputStream;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.annotation.Resource;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.ejb.SessionContext;
/*  22:    */ import javax.ejb.Stateless;
/*  23:    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  24:    */ 
/*  25:    */ @Stateless
/*  26:    */ public class ServicioAtributoImpl
/*  27:    */   implements ServicioAtributo
/*  28:    */ {
/*  29:    */   @Resource
/*  30:    */   protected SessionContext context;
/*  31:    */   @EJB
/*  32:    */   private AtributoDao atributoDao;
/*  33:    */   @EJB
/*  34:    */   private ValorAtributoDao valorAtributoDao;
/*  35:    */   @EJB
/*  36:    */   ServicioValorAtributo servicioValorAtributo;
/*  37:    */   @EJB
/*  38:    */   ServicioProductoAtributo servicioProductoAtributo;
/*  39:    */   
/*  40:    */   public void guardar(Atributo atributo)
/*  41:    */   {
/*  42: 65 */     for (ValorAtributo valorAtributo : atributo.getListaValorAtributo())
/*  43:    */     {
/*  44: 66 */       this.valorAtributoDao.guardar(valorAtributo);
/*  45: 67 */       if ((valorAtributo.isEditado()) && (valorAtributo != null)) {
/*  46: 68 */         this.servicioProductoAtributo.actualizarProductoAtributo(valorAtributo);
/*  47:    */       }
/*  48:    */     }
/*  49: 71 */     this.atributoDao.guardar(atributo);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void eliminar(Atributo atributo)
/*  53:    */   {
/*  54: 80 */     this.atributoDao.eliminar(atributo);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Atributo buscarPorId(int id)
/*  58:    */   {
/*  59: 90 */     return (Atributo)this.atributoDao.buscarPorId(Integer.valueOf(id));
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<Atributo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  63:    */   {
/*  64: 96 */     return this.atributoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public List<Atributo> obtenerListaComboPorIndicador(int idOrganizacion, boolean indicadorProducto, boolean indicadorCliente, boolean indicadorProveedor)
/*  68:    */   {
/*  69:102 */     List<Atributo> lresult = new ArrayList();
/*  70:103 */     if (indicadorProducto) {
/*  71:104 */       lresult.addAll(this.atributoDao.obtenerListaComboIndicadorProducto(idOrganizacion));
/*  72:    */     }
/*  73:106 */     if (indicadorCliente) {
/*  74:107 */       lresult.addAll(this.atributoDao.obtenerListaComboIndicadorCliente(idOrganizacion));
/*  75:    */     }
/*  76:109 */     if (indicadorProveedor) {
/*  77:110 */       lresult.addAll(this.atributoDao.obtenerListaComboIndicadorProveedor(idOrganizacion));
/*  78:    */     }
/*  79:113 */     return lresult;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public List<Atributo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  83:    */   {
/*  84:123 */     return this.atributoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int contarPorCriterio(Map<String, String> filters)
/*  88:    */   {
/*  89:133 */     return this.atributoDao.contarPorCriterio(filters);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Atributo cargarDetalle(int idAtributo)
/*  93:    */   {
/*  94:143 */     return this.atributoDao.cargarDetalle(idAtributo);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void cargarDetalleAtributo(Atributo atributo, int idOrganizacion, String fileName, InputStream imInputStream, int filaInicial)
/*  98:    */     throws ExcepcionAS2
/*  99:    */   {
/* 100:149 */     int filaActual = filaInicial;
/* 101:150 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 102:151 */     int columnaErronea = -1;
/* 103:    */     try
/* 104:    */     {
/* 105:155 */       HashMap<String, ValorAtributo> hmValorAtributo = new HashMap();
/* 106:156 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(idOrganizacion, fileName, imInputStream, 4, 0);
/* 107:158 */       for (HSSFCell[] fila : datos)
/* 108:    */       {
/* 109:159 */         filaErronea = fila;
/* 110:160 */         columnaErronea = 0;
/* 111:161 */         String codigoAtributo = fila[0] != null ? fila[0].getStringCellValue().trim() : "";
/* 112:162 */         columnaErronea = 1;
/* 113:163 */         String nombreAtributo = fila[1] != null ? fila[1].getStringCellValue().trim() : "";
/* 114:164 */         columnaErronea = 2;
/* 115:165 */         String notaAtributo = fila[2] != null ? fila[2].getStringCellValue().trim() : "";
/* 116:    */         
/* 117:167 */         ValorAtributo valor = (ValorAtributo)hmValorAtributo.get(codigoAtributo);
/* 118:168 */         if (valor == null)
/* 119:    */         {
/* 120:169 */           valor = new ValorAtributo();
/* 121:170 */           valor.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 122:171 */           valor.setCodigo(codigoAtributo);
/* 123:172 */           valor.setNombre(nombreAtributo);
/* 124:173 */           valor.setDescripcion(notaAtributo);
/* 125:174 */           valor.setActivo(true);
/* 126:175 */           valor.setAtributo(atributo);
/* 127:    */           
/* 128:177 */           atributo.getListaValorAtributo().add(valor);
/* 129:178 */           hmValorAtributo.put(codigoAtributo, valor);
/* 130:    */         }
/* 131:181 */         filaActual++;
/* 132:    */       }
/* 133:    */     }
/* 134:    */     catch (IllegalArgumentException e)
/* 135:    */     {
/* 136:184 */       this.context.setRollbackOnly();
/* 137:185 */       throw new ExcepcionAS2("msg_error_formato_incorrecto");
/* 138:    */     }
/* 139:    */     catch (Exception e)
/* 140:    */     {
/* 141:187 */       this.context.setRollbackOnly();
/* 142:188 */       throw new ExcepcionAS2(e);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public List getReporteAtributo(int idAtributo)
/* 147:    */   {
/* 148:199 */     return this.atributoDao.getReporteAtributo(idAtributo);
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioAtributoImpl
 * JD-Core Version:    0.7.0.1
 */