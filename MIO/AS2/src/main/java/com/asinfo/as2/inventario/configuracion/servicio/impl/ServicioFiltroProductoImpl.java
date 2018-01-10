/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.FiltroProductoDao;
/*   4:    */ import com.asinfo.as2.entities.Atributo;
/*   5:    */ import com.asinfo.as2.entities.FiltroProducto;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioFiltroProducto;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ServicioFiltroProductoImpl
/*  16:    */   implements ServicioFiltroProducto
/*  17:    */ {
/*  18:    */   @EJB
/*  19:    */   private FiltroProductoDao filtroProductoDao;
/*  20:    */   
/*  21:    */   public void guardar(FiltroProducto filtroProducto)
/*  22:    */     throws ExcepcionAS2
/*  23:    */   {
/*  24: 39 */     validar(filtroProducto);
/*  25: 40 */     this.filtroProductoDao.guardar(filtroProducto);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void eliminar(FiltroProducto filtroProducto)
/*  29:    */   {
/*  30: 53 */     this.filtroProductoDao.eliminar(filtroProducto);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public FiltroProducto buscarPorId(Integer id)
/*  34:    */   {
/*  35: 65 */     return (FiltroProducto)this.filtroProductoDao.buscarPorId(id);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<FiltroProducto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 76 */     return this.filtroProductoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<FiltroProducto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  44:    */   {
/*  45: 88 */     return this.filtroProductoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int contarPorCriterio(Map<String, String> filters)
/*  49:    */   {
/*  50:100 */     return this.filtroProductoDao.contarPorCriterio(filters);
/*  51:    */   }
/*  52:    */   
/*  53:    */   private void validar(FiltroProducto filtroProducto)
/*  54:    */     throws ExcepcionAS2
/*  55:    */   {
/*  56:105 */     if ((filtroProducto.isIndicadorAtributo1()) && (filtroProducto.getAtributo1() == null)) {
/*  57:106 */       throw new ExcepcionAS2("msg_error_dato_obligatorio", ": Atributo 1");
/*  58:    */     }
/*  59:108 */     if ((filtroProducto.isIndicadorAtributo2()) && (filtroProducto.getAtributo2() == null)) {
/*  60:109 */       throw new ExcepcionAS2("msg_error_dato_obligatorio", ": Atributo 2");
/*  61:    */     }
/*  62:111 */     if ((filtroProducto.isIndicadorAtributo3()) && (filtroProducto.getAtributo3() == null)) {
/*  63:112 */       throw new ExcepcionAS2("msg_error_dato_obligatorio", ": Atributo 3");
/*  64:    */     }
/*  65:114 */     if ((filtroProducto.isIndicadorAtributo4()) && (filtroProducto.getAtributo4() == null)) {
/*  66:115 */       throw new ExcepcionAS2("msg_error_dato_obligatorio", ": Atributo 4");
/*  67:    */     }
/*  68:117 */     if ((filtroProducto.isIndicadorAtributo5()) && (filtroProducto.getAtributo5() == null)) {
/*  69:118 */       throw new ExcepcionAS2("msg_error_dato_obligatorio", ": Atributo 5");
/*  70:    */     }
/*  71:120 */     if ((filtroProducto.isIndicadorAtributo6()) && (filtroProducto.getAtributo6() == null)) {
/*  72:121 */       throw new ExcepcionAS2("msg_error_dato_obligatorio", ": Atributo 6");
/*  73:    */     }
/*  74:123 */     if ((filtroProducto.isIndicadorAtributo7()) && (filtroProducto.getAtributo7() == null)) {
/*  75:124 */       throw new ExcepcionAS2("msg_error_dato_obligatorio", ": Atributo 7");
/*  76:    */     }
/*  77:126 */     if ((filtroProducto.isIndicadorAtributo8()) && (filtroProducto.getAtributo8() == null)) {
/*  78:127 */       throw new ExcepcionAS2("msg_error_dato_obligatorio", ": Atributo 8");
/*  79:    */     }
/*  80:129 */     if ((filtroProducto.isIndicadorAtributo9()) && (filtroProducto.getAtributo9() == null)) {
/*  81:130 */       throw new ExcepcionAS2("msg_error_dato_obligatorio", ": Atributo 9");
/*  82:    */     }
/*  83:132 */     if ((filtroProducto.isIndicadorAtributo10()) && (filtroProducto.getAtributo10() == null)) {
/*  84:133 */       throw new ExcepcionAS2("msg_error_dato_obligatorio", ": Atributo 10");
/*  85:    */     }
/*  86:136 */     HashMap<Integer, Atributo> hmAtributo = new HashMap();
/*  87:138 */     if (filtroProducto.getAtributo1() != null)
/*  88:    */     {
/*  89:139 */       if (hmAtributo.containsKey(Integer.valueOf(filtroProducto.getAtributo1().getId()))) {
/*  90:140 */         throw new ExcepcionAS2("msg_info_existe_atributo", " " + filtroProducto.getAtributo1().getNombre());
/*  91:    */       }
/*  92:142 */       hmAtributo.put(Integer.valueOf(filtroProducto.getAtributo1().getId()), filtroProducto.getAtributo1());
/*  93:    */     }
/*  94:145 */     if (filtroProducto.getAtributo2() != null)
/*  95:    */     {
/*  96:146 */       if (hmAtributo.containsKey(Integer.valueOf(filtroProducto.getAtributo2().getId()))) {
/*  97:147 */         throw new ExcepcionAS2("msg_info_existe_atributo", " " + filtroProducto.getAtributo2().getNombre());
/*  98:    */       }
/*  99:149 */       hmAtributo.put(Integer.valueOf(filtroProducto.getAtributo2().getId()), filtroProducto.getAtributo2());
/* 100:    */     }
/* 101:152 */     if (filtroProducto.getAtributo3() != null)
/* 102:    */     {
/* 103:153 */       if (hmAtributo.containsKey(Integer.valueOf(filtroProducto.getAtributo3().getId()))) {
/* 104:154 */         throw new ExcepcionAS2("msg_info_existe_atributo", " " + filtroProducto.getAtributo3().getNombre());
/* 105:    */       }
/* 106:156 */       hmAtributo.put(Integer.valueOf(filtroProducto.getAtributo3().getId()), filtroProducto.getAtributo3());
/* 107:    */     }
/* 108:159 */     if (filtroProducto.getAtributo4() != null)
/* 109:    */     {
/* 110:160 */       if (hmAtributo.containsKey(Integer.valueOf(filtroProducto.getAtributo4().getId()))) {
/* 111:161 */         throw new ExcepcionAS2("msg_info_existe_atributo", " " + filtroProducto.getAtributo4().getNombre());
/* 112:    */       }
/* 113:163 */       hmAtributo.put(Integer.valueOf(filtroProducto.getAtributo4().getId()), filtroProducto.getAtributo4());
/* 114:    */     }
/* 115:166 */     if (filtroProducto.getAtributo5() != null)
/* 116:    */     {
/* 117:167 */       if (hmAtributo.containsKey(Integer.valueOf(filtroProducto.getAtributo5().getId()))) {
/* 118:168 */         throw new ExcepcionAS2("msg_info_existe_atributo", " " + filtroProducto.getAtributo5().getNombre());
/* 119:    */       }
/* 120:170 */       hmAtributo.put(Integer.valueOf(filtroProducto.getAtributo5().getId()), filtroProducto.getAtributo5());
/* 121:    */     }
/* 122:173 */     if (filtroProducto.getAtributo6() != null)
/* 123:    */     {
/* 124:174 */       if (hmAtributo.containsKey(Integer.valueOf(filtroProducto.getAtributo6().getId()))) {
/* 125:175 */         throw new ExcepcionAS2("msg_info_existe_atributo", " " + filtroProducto.getAtributo6().getNombre());
/* 126:    */       }
/* 127:177 */       hmAtributo.put(Integer.valueOf(filtroProducto.getAtributo6().getId()), filtroProducto.getAtributo6());
/* 128:    */     }
/* 129:180 */     if (filtroProducto.getAtributo7() != null)
/* 130:    */     {
/* 131:181 */       if (hmAtributo.containsKey(Integer.valueOf(filtroProducto.getAtributo7().getId()))) {
/* 132:182 */         throw new ExcepcionAS2("msg_info_existe_atributo", " " + filtroProducto.getAtributo7().getNombre());
/* 133:    */       }
/* 134:184 */       hmAtributo.put(Integer.valueOf(filtroProducto.getAtributo7().getId()), filtroProducto.getAtributo7());
/* 135:    */     }
/* 136:187 */     if (filtroProducto.getAtributo8() != null)
/* 137:    */     {
/* 138:188 */       if (hmAtributo.containsKey(Integer.valueOf(filtroProducto.getAtributo8().getId()))) {
/* 139:189 */         throw new ExcepcionAS2("msg_info_existe_atributo", " " + filtroProducto.getAtributo8().getNombre());
/* 140:    */       }
/* 141:191 */       hmAtributo.put(Integer.valueOf(filtroProducto.getAtributo8().getId()), filtroProducto.getAtributo8());
/* 142:    */     }
/* 143:194 */     if (filtroProducto.getAtributo9() != null)
/* 144:    */     {
/* 145:195 */       if (hmAtributo.containsKey(Integer.valueOf(filtroProducto.getAtributo9().getId()))) {
/* 146:196 */         throw new ExcepcionAS2("msg_info_existe_atributo", " " + filtroProducto.getAtributo9().getNombre());
/* 147:    */       }
/* 148:198 */       hmAtributo.put(Integer.valueOf(filtroProducto.getAtributo9().getId()), filtroProducto.getAtributo9());
/* 149:    */     }
/* 150:201 */     if (filtroProducto.getAtributo10() != null)
/* 151:    */     {
/* 152:202 */       if (hmAtributo.containsKey(Integer.valueOf(filtroProducto.getAtributo10().getId()))) {
/* 153:203 */         throw new ExcepcionAS2("msg_info_existe_atributo", " " + filtroProducto.getAtributo10().getNombre());
/* 154:    */       }
/* 155:205 */       hmAtributo.put(Integer.valueOf(filtroProducto.getAtributo10().getId()), filtroProducto.getAtributo10());
/* 156:    */     }
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioFiltroProductoImpl
 * JD-Core Version:    0.7.0.1
 */