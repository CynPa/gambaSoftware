/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CriterioDistribucionDao;
/*   4:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   5:    */ import com.asinfo.as2.entities.Bodega;
/*   6:    */ import com.asinfo.as2.entities.Canal;
/*   7:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*   8:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   9:    */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*  10:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  11:    */ import com.asinfo.as2.entities.ConceptoContable;
/*  12:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*  13:    */ import com.asinfo.as2.entities.Departamento;
/*  14:    */ import com.asinfo.as2.entities.DestinoCosto;
/*  15:    */ import com.asinfo.as2.entities.DimensionContable;
/*  16:    */ import com.asinfo.as2.entities.Documento;
/*  17:    */ import com.asinfo.as2.entities.Empleado;
/*  18:    */ import com.asinfo.as2.entities.Empresa;
/*  19:    */ import com.asinfo.as2.entities.Impuesto;
/*  20:    */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*  21:    */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*  22:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  23:    */ import com.asinfo.as2.entities.MotivoNotaCreditoProveedor;
/*  24:    */ import com.asinfo.as2.entities.Producto;
/*  25:    */ import com.asinfo.as2.entities.Rubro;
/*  26:    */ import com.asinfo.as2.entities.SubcategoriaActivo;
/*  27:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  28:    */ import com.asinfo.as2.entities.Subempresa;
/*  29:    */ import com.asinfo.as2.entities.Sucursal;
/*  30:    */ import com.asinfo.as2.entities.Zona;
/*  31:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  32:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  33:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  34:    */ import java.util.HashMap;
/*  35:    */ import java.util.List;
/*  36:    */ import java.util.Map;
/*  37:    */ import javax.ejb.EJB;
/*  38:    */ import javax.ejb.Stateless;
/*  39:    */ 
/*  40:    */ @Stateless
/*  41:    */ public class ServicioCriterioDistribucionImpl
/*  42:    */   implements ServicioCriterioDistribucion
/*  43:    */ {
/*  44:    */   @EJB
/*  45:    */   private CriterioDistribucionDao criterioDistribucionDao;
/*  46:    */   
/*  47:    */   public void guardar(CriterioDistribucion criterioDistribucion)
/*  48:    */     throws ExcepcionAS2Financiero
/*  49:    */   {
/*  50: 48 */     this.criterioDistribucionDao.guardar(criterioDistribucion);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void guardarListaCriterioDistribucion(List<CriterioDistribucion> listaCriterioDistribucion, String validaDimension1, String validaDimension2, String validaDimension3, String validaDimension4, String validaDimension5)
/*  54:    */     throws ExcepcionAS2Financiero
/*  55:    */   {
/*  56: 61 */     for (CriterioDistribucion criterioDistribucion : listaCriterioDistribucion)
/*  57:    */     {
/*  58: 63 */       if (!criterioDistribucion.isEliminado()) {
/*  59: 65 */         if ((!validaDimension1.isEmpty()) || (!validaDimension2.isEmpty()) || (!validaDimension3.isEmpty()) || (!validaDimension4.isEmpty()) || 
/*  60: 66 */           (!validaDimension5.isEmpty())) {
/*  61: 68 */           if ((criterioDistribucion.getDimensionContable1() == null) && (criterioDistribucion.getDimensionContable2() == null) && 
/*  62: 69 */             (criterioDistribucion.getDimensionContable3() == null) && (criterioDistribucion.getDimensionContable4() == null) && 
/*  63: 70 */             (criterioDistribucion.getDimensionContable5() == null)) {
/*  64: 71 */             throw new ExcepcionAS2Financiero("msg_error_dimension_contable_en_criterio_distribucion");
/*  65:    */           }
/*  66:    */         }
/*  67:    */       }
/*  68: 76 */       this.criterioDistribucionDao.guardar(criterioDistribucion);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public CriterioDistribucion buscarPorId(Integer id)
/*  73:    */   {
/*  74: 87 */     return (CriterioDistribucion)this.criterioDistribucionDao.buscarPorId(id);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List<CriterioDistribucion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  78:    */   {
/*  79: 99 */     return this.criterioDistribucionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public List<CriterioDistribucion> obtenerListaPorDocumentoBase(int idOrganizacion, DocumentoBase documentoBase)
/*  83:    */   {
/*  84:104 */     Map<String, String> filtros = new HashMap();
/*  85:105 */     filtros.put("idOrganizacion", String.valueOf(idOrganizacion));
/*  86:106 */     filtros.put("documentoBase", documentoBase.toString());
/*  87:107 */     List<CriterioDistribucion> lista = obtenerListaCombo(null, true, filtros);
/*  88:108 */     for (CriterioDistribucion criterioDistribucion : lista)
/*  89:    */     {
/*  90:109 */       criterioDistribucion.getIdCriterioDistribucion();
/*  91:110 */       if (criterioDistribucion.getDimensionContable1() != null) {
/*  92:111 */         criterioDistribucion.getDimensionContable1().getId();
/*  93:    */       }
/*  94:113 */       if (criterioDistribucion.getDimensionContable2() != null) {
/*  95:114 */         criterioDistribucion.getDimensionContable2().getId();
/*  96:    */       }
/*  97:116 */       if (criterioDistribucion.getDimensionContable3() != null) {
/*  98:117 */         criterioDistribucion.getDimensionContable3().getId();
/*  99:    */       }
/* 100:119 */       if (criterioDistribucion.getDimensionContable4() != null) {
/* 101:120 */         criterioDistribucion.getDimensionContable4().getId();
/* 102:    */       }
/* 103:122 */       if (criterioDistribucion.getDimensionContable5() != null) {
/* 104:123 */         criterioDistribucion.getDimensionContable5().getId();
/* 105:    */       }
/* 106:126 */       if (criterioDistribucion.getDocumento() != null) {
/* 107:127 */         criterioDistribucion.getDocumento().getIdDocumento();
/* 108:    */       }
/* 109:129 */       if (criterioDistribucion.getSucursal() != null) {
/* 110:130 */         criterioDistribucion.getSucursal().getIdSucursal();
/* 111:    */       }
/* 112:132 */       if (criterioDistribucion.getCategoriaEmpresa() != null) {
/* 113:133 */         criterioDistribucion.getCategoriaEmpresa().getIdCategoriaEmpresa();
/* 114:    */       }
/* 115:135 */       if (criterioDistribucion.getCliente() != null) {
/* 116:136 */         criterioDistribucion.getCliente().getIdEmpresa();
/* 117:    */       }
/* 118:138 */       if (criterioDistribucion.getProveedor() != null) {
/* 119:139 */         criterioDistribucion.getProveedor().getIdEmpresa();
/* 120:    */       }
/* 121:141 */       if (criterioDistribucion.getCategoriaProducto() != null) {
/* 122:142 */         criterioDistribucion.getCategoriaProducto().getIdCategoriaProducto();
/* 123:    */       }
/* 124:144 */       if (criterioDistribucion.getSubcategoriaProducto() != null) {
/* 125:145 */         criterioDistribucion.getSubcategoriaProducto().getIdSubcategoriaProducto();
/* 126:    */       }
/* 127:147 */       if (criterioDistribucion.getProducto() != null) {
/* 128:148 */         criterioDistribucion.getProducto().getIdProducto();
/* 129:    */       }
/* 130:150 */       if (criterioDistribucion.getBodega() != null) {
/* 131:151 */         criterioDistribucion.getBodega().getIdBodega();
/* 132:    */       }
/* 133:153 */       if (criterioDistribucion.getCanal() != null) {
/* 134:154 */         criterioDistribucion.getCanal().getIdCanal();
/* 135:    */       }
/* 136:156 */       if (criterioDistribucion.getSubcliente() != null) {
/* 137:157 */         criterioDistribucion.getSubcliente().getIdSubempresa();
/* 138:    */       }
/* 139:159 */       if (criterioDistribucion.getSubproveedor() != null) {
/* 140:160 */         criterioDistribucion.getSubproveedor().getIdSubempresa();
/* 141:    */       }
/* 142:162 */       if (criterioDistribucion.getZona() != null) {
/* 143:163 */         criterioDistribucion.getZona().getIdZona();
/* 144:    */       }
/* 145:165 */       if (criterioDistribucion.getMotivoNotaCreditoCliente() != null) {
/* 146:166 */         criterioDistribucion.getMotivoNotaCreditoCliente().getIdMotivoNotaCreditoCliente();
/* 147:    */       }
/* 148:168 */       if (criterioDistribucion.getMotivoNotaCreditoProveedor() != null) {
/* 149:169 */         criterioDistribucion.getMotivoNotaCreditoProveedor().getIdMotivoNotaCreditoProveedor();
/* 150:    */       }
/* 151:171 */       if (criterioDistribucion.getMotivoAjusteInventario() != null) {
/* 152:172 */         criterioDistribucion.getMotivoAjusteInventario().getIdMotivoAjusteInventario();
/* 153:    */       }
/* 154:174 */       if (criterioDistribucion.getMotivoBajaActivo() != null) {
/* 155:175 */         criterioDistribucion.getMotivoBajaActivo().getIdMotivoBajaActivo();
/* 156:    */       }
/* 157:177 */       if (criterioDistribucion.getCategoriaActivo() != null) {
/* 158:178 */         criterioDistribucion.getCategoriaActivo().getIdCategoriaActivo();
/* 159:    */       }
/* 160:180 */       if (criterioDistribucion.getSubcategoriaActivo() != null) {
/* 161:181 */         criterioDistribucion.getSubcategoriaActivo().getIdSubcategoriaActivo();
/* 162:    */       }
/* 163:183 */       if (criterioDistribucion.getActivoFijo() != null) {
/* 164:184 */         criterioDistribucion.getActivoFijo().getIdActivoFijo();
/* 165:    */       }
/* 166:186 */       if (criterioDistribucion.getEmpleado() != null) {
/* 167:187 */         criterioDistribucion.getEmpleado().getIdEmpleado();
/* 168:    */       }
/* 169:189 */       if (criterioDistribucion.getRubro() != null) {
/* 170:190 */         criterioDistribucion.getRubro().getIdRubro();
/* 171:    */       }
/* 172:192 */       if (criterioDistribucion.getDepartamento() != null) {
/* 173:193 */         criterioDistribucion.getDepartamento().getIdDepartamento();
/* 174:    */       }
/* 175:195 */       if (criterioDistribucion.getConceptoContable() != null) {
/* 176:196 */         criterioDistribucion.getConceptoContable().getIdConceptoContable();
/* 177:    */       }
/* 178:198 */       if (criterioDistribucion.getDestinoCosto() != null) {
/* 179:199 */         criterioDistribucion.getDestinoCosto().getIdDestinoCosto();
/* 180:    */       }
/* 181:201 */       if (criterioDistribucion.getCategoriaImpuesto() != null) {
/* 182:202 */         criterioDistribucion.getCategoriaImpuesto().getIdCategoriaImpuesto();
/* 183:    */       }
/* 184:204 */       if (criterioDistribucion.getImpuesto() != null) {
/* 185:205 */         criterioDistribucion.getImpuesto().getIdImpuesto();
/* 186:    */       }
/* 187:    */     }
/* 188:209 */     return lista;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List<CriterioDistribucion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 192:    */   {
/* 193:221 */     return this.criterioDistribucionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public int contarPorCriterio(Map<String, String> filters)
/* 197:    */   {
/* 198:231 */     return this.criterioDistribucionDao.contarPorCriterio(filters);
/* 199:    */   }
/* 200:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioCriterioDistribucionImpl
 * JD-Core Version:    0.7.0.1
 */