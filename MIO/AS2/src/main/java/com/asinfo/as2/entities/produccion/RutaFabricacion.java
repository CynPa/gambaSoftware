/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="ruta_fabricacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  23:    */ public class RutaFabricacion
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 2937943982015257899L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="ruta_fabricacion", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="ruta_fabricacion")
/*  30:    */   @Column(name="id_ruta_fabricacion")
/*  31:    */   private int idRutaFabricacion;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="codigo", nullable=false, length=10)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=10)
/*  39:    */   private String codigo;
/*  40:    */   @Column(name="nombre", nullable=false, length=50)
/*  41:    */   @NotNull
/*  42:    */   @Size(min=2, max=50)
/*  43:    */   private String nombre;
/*  44:    */   @Column(name="descripcion", length=200)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   private boolean activo;
/*  49:    */   @Column(name="predeterminado", nullable=false)
/*  50:    */   private boolean predeterminado;
/*  51:    */   @Column(name="tipo_ciclo_produccion_enum", nullable=true)
/*  52:    */   @Enumerated(EnumType.ORDINAL)
/*  53:    */   private TipoCicloProduccionEnum tipoCicloProduccionEnum;
/*  54:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="rutaFabricacion")
/*  55: 93 */   private List<OperacionProduccion> listaOperacionProduccion = new ArrayList();
/*  56:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="rutaFabricacion")
/*  57: 96 */   private List<ProductoRutaFabricacion> listaProductoRutaFabricacion = new ArrayList();
/*  58:    */   
/*  59:    */   public int getId()
/*  60:    */   {
/*  61:118 */     return this.idRutaFabricacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdRutaFabricacion()
/*  65:    */   {
/*  66:127 */     return this.idRutaFabricacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdRutaFabricacion(int idRutaFabricacion)
/*  70:    */   {
/*  71:137 */     this.idRutaFabricacion = idRutaFabricacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdOrganizacion()
/*  75:    */   {
/*  76:146 */     return this.idOrganizacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdOrganizacion(int idOrganizacion)
/*  80:    */   {
/*  81:156 */     this.idOrganizacion = idOrganizacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getIdSucursal()
/*  85:    */   {
/*  86:165 */     return this.idSucursal;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdSucursal(int idSucursal)
/*  90:    */   {
/*  91:175 */     this.idSucursal = idSucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getCodigo()
/*  95:    */   {
/*  96:184 */     return this.codigo;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setCodigo(String codigo)
/* 100:    */   {
/* 101:194 */     this.codigo = codigo;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getNombre()
/* 105:    */   {
/* 106:203 */     return this.nombre;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setNombre(String nombre)
/* 110:    */   {
/* 111:213 */     this.nombre = nombre;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getDescripcion()
/* 115:    */   {
/* 116:222 */     return this.descripcion;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setDescripcion(String descripcion)
/* 120:    */   {
/* 121:232 */     this.descripcion = descripcion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isActivo()
/* 125:    */   {
/* 126:241 */     return this.activo;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setActivo(boolean activo)
/* 130:    */   {
/* 131:251 */     this.activo = activo;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean isPredeterminado()
/* 135:    */   {
/* 136:260 */     return this.predeterminado;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setPredeterminado(boolean predeterminado)
/* 140:    */   {
/* 141:270 */     this.predeterminado = predeterminado;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<ProductoRutaFabricacion> getListaProductoRutaFabricacion()
/* 145:    */   {
/* 146:279 */     return this.listaProductoRutaFabricacion;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setListaProductoRutaFabricacion(List<ProductoRutaFabricacion> listaProductoRutaFabricacion)
/* 150:    */   {
/* 151:289 */     this.listaProductoRutaFabricacion = listaProductoRutaFabricacion;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<OperacionProduccion> getListaOperacionProduccion()
/* 155:    */   {
/* 156:298 */     return this.listaOperacionProduccion;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setListaOperacionProduccion(List<OperacionProduccion> listaOperacionProduccion)
/* 160:    */   {
/* 161:308 */     this.listaOperacionProduccion = listaOperacionProduccion;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public TipoCicloProduccionEnum getTipoCicloProduccionEnum()
/* 165:    */   {
/* 166:317 */     return this.tipoCicloProduccionEnum;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setTipoCicloProduccionEnum(TipoCicloProduccionEnum tipoCicloProduccionEnum)
/* 170:    */   {
/* 171:327 */     this.tipoCicloProduccionEnum = tipoCicloProduccionEnum;
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.RutaFabricacion
 * JD-Core Version:    0.7.0.1
 */