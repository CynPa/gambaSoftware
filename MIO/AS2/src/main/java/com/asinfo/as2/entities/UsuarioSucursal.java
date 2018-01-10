/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="usuario_sucursal", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_usuario", "id_sucursal"})})
/*  17:    */ public class UsuarioSucursal
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = -2984468711000714833L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="usuario_sucursal", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="usuario_sucursal")
/*  24:    */   @Column(name="id_usuario_sucursal")
/*  25:    */   private int idUsuarioSucursal;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @ManyToOne
/*  29:    */   @JoinColumn(name="id_usuario", nullable=true)
/*  30:    */   private EntidadUsuario entidadUsuario;
/*  31:    */   @ManyToOne
/*  32:    */   @JoinColumn(name="id_sucursal", nullable=true)
/*  33:    */   private Sucursal sucursal;
/*  34:    */   @Column(name="predeterminado", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private boolean predeterminado;
/*  37:    */   
/*  38:    */   public int getId()
/*  39:    */   {
/*  40: 85 */     return this.idUsuarioSucursal;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getIdUsuarioSucursal()
/*  44:    */   {
/*  45: 94 */     return this.idUsuarioSucursal;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setIdUsuarioSucursal(int idUsuarioSucursal)
/*  49:    */   {
/*  50:104 */     this.idUsuarioSucursal = idUsuarioSucursal;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getIdOrganizacion()
/*  54:    */   {
/*  55:113 */     return this.idOrganizacion;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdOrganizacion(int idOrganizacion)
/*  59:    */   {
/*  60:123 */     this.idOrganizacion = idOrganizacion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public EntidadUsuario getEntidadUsuario()
/*  64:    */   {
/*  65:132 */     return this.entidadUsuario;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setEntidadUsuario(EntidadUsuario entidadUsuario)
/*  69:    */   {
/*  70:142 */     this.entidadUsuario = entidadUsuario;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Sucursal getSucursal()
/*  74:    */   {
/*  75:151 */     return this.sucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setSucursal(Sucursal sucursal)
/*  79:    */   {
/*  80:161 */     this.sucursal = sucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean isPredeterminado()
/*  84:    */   {
/*  85:170 */     return this.predeterminado;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setPredeterminado(boolean predeterminado)
/*  89:    */   {
/*  90:180 */     this.predeterminado = predeterminado;
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.UsuarioSucursal
 * JD-Core Version:    0.7.0.1
 */