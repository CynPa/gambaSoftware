/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
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
/*  12:    */ import javax.validation.constraints.Digits;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="tarifa_salarial")
/*  18:    */ public class TarifaSalarial
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 4820543182583862194L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="tarifa_salarial", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tarifa_salarial")
/*  25:    */   @Column(name="id_tarifa_salarial")
/*  26:    */   private int idTarifaSalarial;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", nullable=false, length=10)
/*  32:    */   @NotNull
/*  33:    */   @Size(min=2, max=10)
/*  34:    */   private String codigo;
/*  35:    */   @Column(name="nombre", nullable=false, length=50)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=2, max=50)
/*  38:    */   private String nombre;
/*  39:    */   @Column(name="descripcion", length=200)
/*  40:    */   @Size(max=200)
/*  41:    */   private String descripcion;
/*  42:    */   @Column(name="activo", nullable=false)
/*  43:    */   private boolean activo;
/*  44:    */   @Column(name="predeterminado", nullable=false)
/*  45:    */   private boolean predeterminado;
/*  46:    */   @Column(name="costo", precision=12, scale=2)
/*  47:    */   @Digits(integer=12, fraction=2)
/*  48:    */   private BigDecimal costo;
/*  49:    */   @Column(name="precio", precision=12, scale=2)
/*  50:    */   @Digits(integer=12, fraction=2)
/*  51:    */   private BigDecimal precio;
/*  52:    */   
/*  53:    */   public int getIdTarifaSalarial()
/*  54:    */   {
/*  55:114 */     return this.idTarifaSalarial;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdTarifaSalarial(int idTarifaSalarial)
/*  59:    */   {
/*  60:124 */     this.idTarifaSalarial = idTarifaSalarial;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdOrganizacion()
/*  64:    */   {
/*  65:133 */     return this.idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdOrganizacion(int idOrganizacion)
/*  69:    */   {
/*  70:143 */     this.idOrganizacion = idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdSucursal()
/*  74:    */   {
/*  75:152 */     return this.idSucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdSucursal(int idSucursal)
/*  79:    */   {
/*  80:162 */     this.idSucursal = idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getCodigo()
/*  84:    */   {
/*  85:171 */     return this.codigo;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setCodigo(String codigo)
/*  89:    */   {
/*  90:181 */     this.codigo = codigo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getNombre()
/*  94:    */   {
/*  95:190 */     return this.nombre;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setNombre(String nombre)
/*  99:    */   {
/* 100:200 */     this.nombre = nombre;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getDescripcion()
/* 104:    */   {
/* 105:209 */     return this.descripcion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setDescripcion(String descripcion)
/* 109:    */   {
/* 110:219 */     this.descripcion = descripcion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isActivo()
/* 114:    */   {
/* 115:228 */     return this.activo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setActivo(boolean activo)
/* 119:    */   {
/* 120:238 */     this.activo = activo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isPredeterminado()
/* 124:    */   {
/* 125:247 */     return this.predeterminado;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setPredeterminado(boolean predeterminado)
/* 129:    */   {
/* 130:257 */     this.predeterminado = predeterminado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public BigDecimal getCosto()
/* 134:    */   {
/* 135:266 */     return this.costo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setCosto(BigDecimal costo)
/* 139:    */   {
/* 140:276 */     this.costo = costo;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public BigDecimal getPrecio()
/* 144:    */   {
/* 145:285 */     return this.precio;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setPrecio(BigDecimal precio)
/* 149:    */   {
/* 150:295 */     this.precio = precio;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public int getId()
/* 154:    */   {
/* 155:305 */     return this.idTarifaSalarial;
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.TarifaSalarial
 * JD-Core Version:    0.7.0.1
 */