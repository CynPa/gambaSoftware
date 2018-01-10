/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.persistence.Transient;
/*  15:    */ import javax.validation.constraints.Digits;
/*  16:    */ import javax.validation.constraints.Min;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="rubro_empleado", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_sucursal", "id_empleado", "id_rubro"})})
/*  21:    */ public class RubroEmpleado
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="rubro_empleado", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="rubro_empleado")
/*  28:    */   @Column(name="id_rubro_empleado")
/*  29:    */   private int idRubroEmpleado;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="valor", precision=12, scale=2)
/*  35:    */   @Digits(integer=12, fraction=2)
/*  36:    */   @Min(0L)
/*  37:    */   private BigDecimal valor;
/*  38:    */   @Transient
/*  39:    */   private boolean aplicarRubroEmpleado;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_empleado", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private Empleado empleado;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_rubro", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private Rubro rubro;
/*  48:    */   @Transient
/*  49:    */   private String nombreRubro;
/*  50:    */   
/*  51:    */   public RubroEmpleado() {}
/*  52:    */   
/*  53:    */   public RubroEmpleado(Empleado empleado)
/*  54:    */   {
/*  55:101 */     this.empleado = empleado;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public RubroEmpleado(int idOrganizacion, Rubro rubro, Empleado empleado)
/*  59:    */   {
/*  60:105 */     this.idOrganizacion = idOrganizacion;
/*  61:106 */     this.rubro = rubro;
/*  62:107 */     this.empleado = empleado;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdRubroEmpleado()
/*  66:    */   {
/*  67:121 */     return this.idRubroEmpleado;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdRubroEmpleado(int idRubroEmpleado)
/*  71:    */   {
/*  72:131 */     this.idRubroEmpleado = idRubroEmpleado;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdOrganizacion()
/*  76:    */   {
/*  77:140 */     return this.idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdOrganizacion(int idOrganizacion)
/*  81:    */   {
/*  82:150 */     this.idOrganizacion = idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdSucursal()
/*  86:    */   {
/*  87:159 */     return this.idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdSucursal(int idSucursal)
/*  91:    */   {
/*  92:169 */     this.idSucursal = idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public BigDecimal getValor()
/*  96:    */   {
/*  97:178 */     return this.valor;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setValor(BigDecimal valor)
/* 101:    */   {
/* 102:188 */     this.valor = valor;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Empleado getEmpleado()
/* 106:    */   {
/* 107:197 */     return this.empleado;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setEmpleado(Empleado empleado)
/* 111:    */   {
/* 112:207 */     this.empleado = empleado;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Rubro getRubro()
/* 116:    */   {
/* 117:216 */     return this.rubro;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setRubro(Rubro rubro)
/* 121:    */   {
/* 122:226 */     this.rubro = rubro;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isAplicarRubroEmpleado()
/* 126:    */   {
/* 127:235 */     return this.aplicarRubroEmpleado;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setAplicarRubroEmpleado(boolean aplicarRubroEmpleado)
/* 131:    */   {
/* 132:245 */     this.aplicarRubroEmpleado = aplicarRubroEmpleado;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getNombreRubro()
/* 136:    */   {
/* 137:249 */     if (this.rubro != null) {
/* 138:250 */       this.nombreRubro = this.rubro.getNombre();
/* 139:    */     }
/* 140:252 */     return this.nombreRubro;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setNombreRubro(String nombreRubro)
/* 144:    */   {
/* 145:256 */     this.nombreRubro = nombreRubro;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int getId()
/* 149:    */   {
/* 150:266 */     return this.idRubroEmpleado;
/* 151:    */   }
/* 152:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RubroEmpleado
 * JD-Core Version:    0.7.0.1
 */