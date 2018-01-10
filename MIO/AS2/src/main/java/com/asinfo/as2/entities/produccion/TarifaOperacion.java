/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.validation.constraints.DecimalMin;
/*  13:    */ import javax.validation.constraints.Digits;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="tarifa_operacion")
/*  19:    */ public class TarifaOperacion
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 5043194962496217985L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="tarifa_operacion", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tarifa_operacion")
/*  26:    */   @Column(name="id_tarifa_operacion")
/*  27:    */   private int idTarifaOperacion;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="codigo", nullable=false, length=10)
/*  33:    */   @NotNull
/*  34:    */   @Size(min=2, max=10)
/*  35:    */   private String codigo;
/*  36:    */   @Column(name="nombre", nullable=false, length=50)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=50)
/*  39:    */   private String nombre;
/*  40:    */   @Column(name="descripcion", length=200)
/*  41:    */   @Size(max=200)
/*  42:    */   private String descripcion;
/*  43:    */   @Column(name="activo", nullable=false)
/*  44:    */   private boolean activo;
/*  45:    */   @Column(name="predeterminado", nullable=false)
/*  46:    */   private boolean predeterminado;
/*  47:    */   @Column(name="costo", precision=12, scale=6)
/*  48:    */   @Digits(integer=12, fraction=6)
/*  49:    */   @DecimalMin("0.000000")
/*  50:    */   private BigDecimal costo;
/*  51:    */   @Column(name="precio", precision=12, scale=6)
/*  52:    */   @Digits(integer=12, fraction=6)
/*  53:    */   @DecimalMin("0.000000")
/*  54:    */   private BigDecimal precio;
/*  55:    */   
/*  56:    */   public TarifaOperacion() {}
/*  57:    */   
/*  58:    */   public TarifaOperacion(int idTarifaOperacion, String nombre)
/*  59:    */   {
/*  60:113 */     this.idTarifaOperacion = idTarifaOperacion;
/*  61:114 */     this.nombre = nombre;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdTarifaOperacion()
/*  65:    */   {
/*  66:127 */     return this.idTarifaOperacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdTarifaOperacion(int idTarifaOperacion)
/*  70:    */   {
/*  71:137 */     this.idTarifaOperacion = idTarifaOperacion;
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
/* 144:    */   public BigDecimal getCosto()
/* 145:    */   {
/* 146:279 */     return this.costo;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setCosto(BigDecimal costo)
/* 150:    */   {
/* 151:289 */     this.costo = costo;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public BigDecimal getPrecio()
/* 155:    */   {
/* 156:298 */     return this.precio;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setPrecio(BigDecimal precio)
/* 160:    */   {
/* 161:308 */     this.precio = precio;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public int getId()
/* 165:    */   {
/* 166:318 */     return this.idTarifaOperacion;
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.TarifaOperacion
 * JD-Core Version:    0.7.0.1
 */