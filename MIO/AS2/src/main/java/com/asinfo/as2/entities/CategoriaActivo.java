/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.OneToMany;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="categoria_activo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  20:    */ public class CategoriaActivo
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="categoria_activo", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="categoria_activo")
/*  27:    */   @Column(name="id_categoria_activo")
/*  28:    */   private int idCategoriaActivo;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="codigo", nullable=false)
/*  34:    */   @Size(min=2, max=10)
/*  35:    */   private String codigo;
/*  36:    */   @Column(name="nombre", nullable=false, length=50)
/*  37:    */   @Size(min=2, max=50)
/*  38:    */   private String nombre;
/*  39:    */   @Column(name="descripcion", nullable=true)
/*  40:    */   @Size(max=200)
/*  41:    */   private String descripcion;
/*  42:    */   @Column(name="activo", nullable=false)
/*  43:    */   private boolean activo;
/*  44:    */   @Column(name="predeterminado", nullable=false)
/*  45:    */   private boolean predeterminado;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_cuenta_contable_activo_fijo", nullable=true)
/*  48:    */   private CuentaContable cuentaContableActivoFijo;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_cuenta_contable_depreciacion", nullable=true)
/*  51:    */   private CuentaContable cuentaContableDepreciacion;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_cuenta_contable_depreciacion_acumulada", nullable=true)
/*  54:    */   private CuentaContable cuentaContableDepreciacionAcumulada;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_cuenta_superavit_por_revalorizacion", nullable=true)
/*  57:    */   private CuentaContable cuentaContableSuperavitPorRevalorizacion;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_cuenta_deficit_por_revalorizacion", nullable=true)
/*  60:    */   private CuentaContable cuentaContableDeficitPorRevalorizacion;
/*  61:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="categoriaActivo")
/*  62: 98 */   private List<SubcategoriaActivo> listaSubcategoriaActivo = new ArrayList();
/*  63:    */   
/*  64:    */   public CategoriaActivo() {}
/*  65:    */   
/*  66:    */   public CategoriaActivo(int idCategoriaActivo, String codigo, String nombre)
/*  67:    */   {
/*  68:124 */     this.idCategoriaActivo = idCategoriaActivo;
/*  69:125 */     this.codigo = codigo;
/*  70:126 */     this.nombre = nombre;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getId()
/*  74:    */   {
/*  75:140 */     return this.idCategoriaActivo;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdCategoriaActivo()
/*  79:    */   {
/*  80:149 */     return this.idCategoriaActivo;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdCategoriaActivo(int idCategoriaActivo)
/*  84:    */   {
/*  85:159 */     this.idCategoriaActivo = idCategoriaActivo;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdOrganizacion()
/*  89:    */   {
/*  90:168 */     return this.idOrganizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdOrganizacion(int idOrganizacion)
/*  94:    */   {
/*  95:178 */     this.idOrganizacion = idOrganizacion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getIdSucursal()
/*  99:    */   {
/* 100:187 */     return this.idSucursal;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setIdSucursal(int idSucursal)
/* 104:    */   {
/* 105:197 */     this.idSucursal = idSucursal;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getNombre()
/* 109:    */   {
/* 110:206 */     return this.nombre;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setNombre(String nombre)
/* 114:    */   {
/* 115:216 */     this.nombre = nombre;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getCodigo()
/* 119:    */   {
/* 120:225 */     return this.codigo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setCodigo(String codigo)
/* 124:    */   {
/* 125:235 */     this.codigo = codigo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getDescripcion()
/* 129:    */   {
/* 130:244 */     return this.descripcion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setDescripcion(String descripcion)
/* 134:    */   {
/* 135:254 */     this.descripcion = descripcion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isActivo()
/* 139:    */   {
/* 140:263 */     return this.activo;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setActivo(boolean activo)
/* 144:    */   {
/* 145:273 */     this.activo = activo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isPredeterminado()
/* 149:    */   {
/* 150:282 */     return this.predeterminado;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setPredeterminado(boolean predeterminado)
/* 154:    */   {
/* 155:292 */     this.predeterminado = predeterminado;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public CuentaContable getCuentaContableActivoFijo()
/* 159:    */   {
/* 160:301 */     return this.cuentaContableActivoFijo;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setCuentaContableActivoFijo(CuentaContable cuentaContableActivoFijo)
/* 164:    */   {
/* 165:311 */     this.cuentaContableActivoFijo = cuentaContableActivoFijo;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public CuentaContable getCuentaContableDepreciacion()
/* 169:    */   {
/* 170:320 */     return this.cuentaContableDepreciacion;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setCuentaContableDepreciacion(CuentaContable cuentaContableDepreciacion)
/* 174:    */   {
/* 175:330 */     this.cuentaContableDepreciacion = cuentaContableDepreciacion;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public CuentaContable getCuentaContableDepreciacionAcumulada()
/* 179:    */   {
/* 180:339 */     return this.cuentaContableDepreciacionAcumulada;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setCuentaContableDepreciacionAcumulada(CuentaContable cuentaContableDepreciacionAcumulada)
/* 184:    */   {
/* 185:349 */     this.cuentaContableDepreciacionAcumulada = cuentaContableDepreciacionAcumulada;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public CuentaContable getCuentaContableSuperavitPorRevalorizacion()
/* 189:    */   {
/* 190:358 */     return this.cuentaContableSuperavitPorRevalorizacion;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setCuentaContableSuperavitPorRevalorizacion(CuentaContable cuentaContableSuperavitPorRevalorizacion)
/* 194:    */   {
/* 195:368 */     this.cuentaContableSuperavitPorRevalorizacion = cuentaContableSuperavitPorRevalorizacion;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public CuentaContable getCuentaContableDeDeficitPorRevalorizacion()
/* 199:    */   {
/* 200:377 */     return this.cuentaContableDeficitPorRevalorizacion;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setCuentaContableDeDeficitPorRevalorizacion(CuentaContable cuentaContableDeDeficitPorRevalorizacion)
/* 204:    */   {
/* 205:387 */     this.cuentaContableDeficitPorRevalorizacion = cuentaContableDeDeficitPorRevalorizacion;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public List<SubcategoriaActivo> getListaSubcategoriaActivo()
/* 209:    */   {
/* 210:396 */     return this.listaSubcategoriaActivo;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setListaSubcategoriaActivo(List<SubcategoriaActivo> listaSubcategoriaActivo)
/* 214:    */   {
/* 215:406 */     this.listaSubcategoriaActivo = listaSubcategoriaActivo;
/* 216:    */   }
/* 217:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CategoriaActivo
 * JD-Core Version:    0.7.0.1
 */